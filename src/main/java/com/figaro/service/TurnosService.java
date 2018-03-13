package com.figaro.service;

import static com.figaro.util.Constants.CATEGORIA_EMPLEADOS;
import static com.figaro.util.Constants.CATEGORIA_TURNOS;
import static com.figaro.util.Constants.MSG_TURNO_OCUPADO_CLIENTE;
import static com.figaro.util.Constants.MSG_TURNO_OCUPADO_EMPLEADO;
import static com.figaro.util.Constants.TIPO_PAGO_CONTADO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.figaro.dto.TurnoDTO;
import com.figaro.exception.HorarioInvalidoException;
import com.figaro.exception.TurnoOcupadoException;
import com.figaro.model.Cliente;
import com.figaro.model.Movimiento;
import com.figaro.model.Turno;
import com.figaro.repository.MovimientosRepository;
import com.figaro.repository.TurnosRepository;

public class TurnosService {
	
	

	final static Logger LOGGER = Logger.getLogger(TurnosService.class);
	
	private ClientesService clientesService;
	private TurnosRepository repository;
	private MovimientosRepository movimientosRepository;
	
	public Turno saveTurno(Turno turno) {
		LOGGER.info("Guardando un nuevo turno: " + turno.toString());
		validateTurno(turno);
		turno.generateTurnoInfo();
		int newID = repository.saveTurno(turno);
		turno.setId(newID);
		return turno ;  
	}

	public Turno pagar(int turnoId){
		Turno turno = getTurno(turnoId);
		return (turno.getPagado()) ?  cancelPago(turnoId) : setPagado(turnoId);
	}
	
	public Turno setPagado(int turnoId) {
		Turno turno = getTurno(turnoId);
		LOGGER.info("Pagando el Turno: " + turno.toString() );
		turno.setPagado(true);
		Movimiento pago = generatePago(turno);
		turno.setPago(pago);
		repository.updateTurnoCobro(turno);
		return turno;
	}

	
	public Turno setCobrado(int turnoId, Movimiento cobro) {
		Turno turno = getTurno(turnoId);
		LOGGER.info("Cobrando el Turno: " + turno.toString() + " Con el movimiento: " + cobro.toString());
		turno.setCobrado(true);
		turno.setCobro(generateCobro(turno,cobro));
		repository.updateTurnoCobro(turno);
		Cliente cliente = turno.getCliente();
		cliente.setUltimaVisita(turno.getDesde());
		clientesService.updateCliente(cliente);
		return turno;
	}
	
	public Turno cancelCobro(int turnoId) {
		Turno turno = getTurno(turnoId);
		LOGGER.info("Cancelando el cobro del Turno: " + turno.toString());
		turno.setCobrado(false);
		turno.setCobro(null);
		repository.updateTurnoCobro(turno);
		return turno;
	}
	
	public Turno cancelPago(int turnoId) {
		Turno turno = getTurno(turnoId);
		LOGGER.info("Cancelando el pago del Turno: " + turno.toString());
		turno.setPagado(false);
		turno.setPago(null);
		repository.updateTurnoCobro(turno);
		return turno;
	}
	
	public Turno updateTurno(Turno turno) {
		LOGGER.info("Actualizando el Turno: " + turno.toString());
		validateTurno(turno);
		updatePago(turno);
		updateCobro(turno);
		Turno updated = getTurno(turno.getId());
		updated.update(turno);
		repository.updateTurno(updated);
		return updated;
	}

	private Movimiento generateCobro(Turno turno,Movimiento cobro) {
		Movimiento movimiento = new Movimiento();
		movimiento.setCategoria(CATEGORIA_TURNOS);
		movimiento.setIsGasto(false);
		movimiento.setFecha(turno.getHasta());
		movimiento.setTipoPago(cobro.getTipoPago());
		movimiento.setCuotas(cobro.getCuotas());
		movimiento.setPrecio(turno.calculatePrecio());
		movimiento.setDescuento(cobro.getDescuento());
		movimiento.descontar();
		movimiento.setDetalle(String.valueOf(turno.getId())) ;
		return movimiento;
	}
	
	
	private Movimiento generatePago(Turno turno) {
		BigDecimal montoTotal = turno.calculatePrecioPago();
		Movimiento movimiento = new Movimiento();
		movimiento.setCategoria(CATEGORIA_EMPLEADOS);
		movimiento.setCuotas(0);
		movimiento.setFecha(new Date());
		movimiento.setIsGasto(true);
		movimiento.setPrecio(montoTotal);
		movimiento.setTipoPago(TIPO_PAGO_CONTADO);
		movimiento.setDetalle(String.valueOf(turno.getId()));
		return movimiento;
	}
	
	
	private void updateCobro(Turno turno) {
		if (turno.getCobrado())
		  turno.setCobro(generateCobro(turno,turno.getCobro()));
	}

	private void updatePago(Turno turno) {
		if (turno.getPagado())
		    turno.setPago(generatePago(turno));	
		}
	
	
	public Turno deleteTurno(int turnoId) {
		Turno turno = getTurno(turnoId);
		LOGGER.info("Eliminando turno: "+turno.toString());
		repository.deleteTurno(turno);
		return turno;
	}
	
	private void validateTurno(Turno nuevoTurno) {
		LOGGER.info("Validando el Turno: " + nuevoTurno.getDesde() +" - " +nuevoTurno.getHasta() +" "+ nuevoTurno.getEmpleado() );
		
		if (horarioInvalido(nuevoTurno))
			throw new HorarioInvalidoException(nuevoTurno.getDesde() +" - "+nuevoTurno.getHasta());
		
		List<Turno> turnosDelDia = repository.searchTurnoValidation(nuevoTurno.getDesde());
		turnosDelDia.remove(nuevoTurno);
		
		for(Turno turno : turnosDelDia) 
			if( (mismoEmpleado(nuevoTurno, turno) || mismoCliente(nuevoTurno, turno)) && horarioOcupado(nuevoTurno, turno))
			throw new TurnoOcupadoException( (mismoEmpleado(nuevoTurno, turno) ? MSG_TURNO_OCUPADO_EMPLEADO : MSG_TURNO_OCUPADO_CLIENTE ));
		
		if (null == nuevoTurno.getCliente())
			nuevoTurno.setCliente(clientesService.getClienteDesconocido());
	}

	
	private boolean mismoEmpleado(Turno nuevoTurno, Turno turno) {
		return turno.getEmpleado().equals(nuevoTurno.getEmpleado());
	}
	
	private boolean mismoCliente(Turno nuevoTurno, Turno turno) {
		return turno.getCliente().equals(nuevoTurno.getCliente());
	}
	
	private boolean horarioOcupado(Turno nuevoTurno, Turno turno) {
		return horarioInicioOcupado(nuevoTurno, turno) || 
			   horarioFinOcupado(nuevoTurno, turno) || 
			   mismoHorario(nuevoTurno, turno) || 
			   dentroDeLaFranja(nuevoTurno, turno);
	}
	
	private boolean dentroDeLaFranja(Turno nuevoTurno, Turno turno) {
		Date inicio = turno.getDesde();
		Date fin    = turno.getHasta();
		return  (nuevoTurno.getDesde().before(inicio) && nuevoTurno.getHasta().after(fin));
	}

	private boolean horarioInicioOcupado(Turno nuevoTurno, Turno turno) {
		Date inicio = turno.getDesde();
		Date fin    = turno.getHasta();
		return  (nuevoTurno.getDesde().equals(inicio) || nuevoTurno.getDesde().after(inicio))  && 
				(nuevoTurno.getDesde().before(fin));
	}
	
	
	private boolean horarioFinOcupado(Turno nuevoTurno, Turno turno) {
		Date inicio = turno.getDesde();
		Date fin    = turno.getHasta();
		return  ( nuevoTurno.getHasta().equals(fin) || nuevoTurno.getHasta().before(fin)) &&
				 nuevoTurno.getHasta().after(inicio);
	}

	private boolean mismoHorario(Turno nuevoTurno, Turno turno) {
		return turno.getDesde().equals(nuevoTurno.getDesde()) && 
			   turno.getHasta().equals(nuevoTurno.getHasta());
	}
	
	private boolean horarioInvalido(Turno nuevoTurno) {
		return nuevoTurno.getDesde().compareTo(nuevoTurno.getHasta()) >= 0;
	}
	
	
	public Turno getTurno(int turnoId) {
		LOGGER.info("Obteniendo el turno con ID: " + turnoId);
		return repository.getTurno(turnoId);
	}
	
	
	public List<TurnoDTO> getTurnosCliente(int clienteId) {
		LOGGER.info("Obteniendo los turnos para el cliente con ID: " +  clienteId);
		return repository.getTurnosCliente(clienteId);
	}
	
	public List<TurnoDTO> getTurnosEmpleado(int empleadoId, int index) {
		LOGGER.info("Obteniendo los turnos para el empleado con ID: " +  empleadoId);
		return repository.getTurnosEmpleado(empleadoId,index);
	}
	
	public List<TurnoDTO> getTurnosEmpleadoSinPagar(int empleadoId) {
		LOGGER.info("Obteniendo los turnos sin pagar para el empleado con ID: " +  empleadoId);
		return repository.getTurnosEmpleadoSinPagar(empleadoId);
	}
	
	public List<TurnoDTO> getTurnosDelDia(Date fecha) {
		LOGGER.info("Obteniendo turnos del dia: " + fecha );
		return searchTurno(fecha);
	}

	public List<TurnoDTO> searchTurno(Date desde) {
		return repository.searchTurno(desde);
	}

	public void setRepository(TurnosRepository repository) {
		this.repository = repository;
	}

	public MovimientosRepository getMovimientosRepository() {
		return movimientosRepository;
	}

	public void setMovimientosRepository(MovimientosRepository movimientosRepository) {
		this.movimientosRepository = movimientosRepository;
	}

	public ClientesService getClientesService() {
		return clientesService;
	}

	public void setClientesService(ClientesService clientesService) {
		this.clientesService = clientesService;
	}

}
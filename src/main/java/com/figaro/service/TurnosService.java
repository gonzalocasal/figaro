package com.figaro.service;

import static com.figaro.util.Constants.CATEGORIA_PELUQUERO;
import static com.figaro.util.Constants.CATEGORIA_TURNOS;
import static com.figaro.util.Constants.FRONT_DATE_FORMAT;
import static com.figaro.util.Constants.TIPO_PAGO_CONTADO;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		movimiento.setDetalle(generateCobroDescripcion(turno)) ;
		return movimiento;
	}
	
	
	private Movimiento generatePago(Turno turno) {
		BigDecimal montoTotal = turno.calculatePrecioPago();
		Movimiento movimiento = new Movimiento();
		movimiento.setCategoria(CATEGORIA_PELUQUERO);
		movimiento.setCuotas(0);
		movimiento.setDetalle(generatePagoDescripcion(turno)); 
		movimiento.setFecha(new Date());
		movimiento.setIsGasto(true);
		movimiento.setPrecio(montoTotal);
		movimiento.setTipoPago(TIPO_PAGO_CONTADO);
		return movimiento;
	}
	
	
	
	private String generateCobroDescripcion (Turno turno){
		String descripionesTrabajo = turno.generateDescripcionTrabajos();
		String cliente = turno.getCliente().getNombre() + " " + turno.getCliente().getApellido();
		return "("+descripionesTrabajo+") "+cliente ;
	}
	
	private String generatePagoDescripcion (Turno turno){
		DateFormat formatter = new SimpleDateFormat(FRONT_DATE_FORMAT);
		String fecha = formatter.format(turno.getHasta());
		String peluquero = turno.getPeluquero().getNombre() +" "+ turno.getPeluquero().getApellido();
		return peluquero+" "+generateCobroDescripcion(turno)+" "+fecha;
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
		LOGGER.debug("Validando el Turno: " + nuevoTurno.getDesde() +" - " +nuevoTurno.getHasta() +" "+ nuevoTurno.getPeluquero() );
		
		if (horarioInvalido(nuevoTurno))
			throw new HorarioInvalidoException(nuevoTurno.getDesde() +" - "+nuevoTurno.getHasta());
		
		List<Turno> turnosDelDia = repository.searchTurnoValidation(nuevoTurno.getDesde());
		turnosDelDia.remove(nuevoTurno);
		
		for(Turno turno : turnosDelDia) 
			if( (mismoPeluquero(nuevoTurno, turno) || mismoCliente(nuevoTurno, turno)) && horarioOcupado(nuevoTurno, turno))
			throw new TurnoOcupadoException(nuevoTurno);
	}

	
	private boolean mismoPeluquero(Turno nuevoTurno, Turno turno) {
		return turno.getPeluquero().equals(nuevoTurno.getPeluquero());
	}
	
	private boolean mismoCliente(Turno nuevoTurno, Turno turno) {
		return turno.getCliente().equals(nuevoTurno.getCliente());
	}
	
	private boolean horarioOcupado(Turno nuevoTurno, Turno turno) {
		return horarioInicioOcupado(nuevoTurno, turno) || 
			   horarioFinOcupado(nuevoTurno, turno) || 
			   mismoHorario(nuevoTurno, turno);
	}
	
	private boolean horarioInicioOcupado(Turno nuevoTurno, Turno turno) {
		return (turno.getDesde().after(nuevoTurno.getDesde()) &&
			   turno.getDesde().before(nuevoTurno.getHasta()) ||
			   turno.getDesde().compareTo(nuevoTurno.getDesde()) == 0);
	}
	
	private boolean horarioFinOcupado(Turno nuevoTurno, Turno turno) {
		return (turno.getHasta().after(nuevoTurno.getDesde()) && 
			   turno.getHasta().before(nuevoTurno.getHasta()) ||
			   turno.getHasta().compareTo(nuevoTurno.getHasta()) == 0);
				
	}

	private boolean mismoHorario(Turno nuevoTurno, Turno turno) {
		return (turno.getDesde().compareTo(nuevoTurno.getDesde()) == 0) && 
			   (turno.getHasta().compareTo(nuevoTurno.getHasta()) == 0);
	}
	
	private boolean horarioInvalido(Turno nuevoTurno) {
		return nuevoTurno.getDesde().compareTo(nuevoTurno.getHasta()) >= 0;
	}
	
	
	public Turno getTurno(int turnoId) {
		LOGGER.debug("Obteniendo el turno con ID: " + turnoId);
		return repository.getTurno(turnoId);
	}
	
	
	public List<TurnoDTO> getTurnosCliente(int clienteId) {
		LOGGER.debug("Obteniendo los turnos para el cliente con ID: " +  clienteId);
		return repository.getTurnosCliente(clienteId);
	}
	
	public List<TurnoDTO> getTurnosPeluquero(int peluqueroId, int index) {
		LOGGER.debug("Obteniendo los turnos para el peluquero con ID: " +  peluqueroId);
		return repository.getTurnosPeluquero(peluqueroId,index);
	}
	
	public List<TurnoDTO> getTurnosPeluqueroSinPagar(int peluqueroId) {
		LOGGER.debug("Obteniendo los turnos sin pagar para el peluquero con ID: " +  peluqueroId);
		return repository.getTurnosPeluqueroSinPagar(peluqueroId);
	}
	public Integer getCantidadTurnosPeluquero(int peluqueroId) {
		LOGGER.debug("Obteniendo la cantidad de turnos para peluquero con ID: " +  peluqueroId);
		return repository.getCantidadTurnosPeluquero(peluqueroId);
	}

	public List<TurnoDTO> getTurnosDelDia(Date fecha) {
		LOGGER.debug("Obteniendo turnos del dia: " + fecha );
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
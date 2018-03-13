package com.figaro.service;

import static com.figaro.util.Constants.HORARIO_RANGO_DEFAULT;
import static com.figaro.util.Constants.SIN_CIUDAD;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.figaro.model.Cliente;
import com.figaro.model.Item;
import com.figaro.model.Movimiento;
import com.figaro.model.Peluquero;
import com.figaro.model.Turno;
import com.figaro.model.Venta;
import com.figaro.repository.EstadisticasRepository;

public class EstadisticasService {

	final static Logger LOGGER = Logger.getLogger(EstadisticasService.class);
	
	private EstadisticasRepository repository;
	private ClientesService clientesService;
	private MovimientosService movimientosService;
	private TurnosService turnosService;
	private VentaService ventasService;
	
	public Map<String, Integer> buscarClienteCiudad(){
		List<Cliente> allClientes = clientesService.getAllClientes();
		Map<String, Integer> mapClientes = new HashMap<>();
		for (Cliente cliente : allClientes) {
			String ciudad = cliente.getDirCiudad();
			Integer cantidadHabitantes = mapClientes.get(ciudad);
			mapClientes.put((null == ciudad) ? SIN_CIUDAD : ciudad , (null == cantidadHabitantes) ? 1 : cantidadHabitantes + 1);
		}
		return mapClientes;
	}
	
	public Map<String, Integer> buscarClienteSexo(){
		List<Cliente> allClientes = clientesService.getAllClientes();
		Map<String, Integer> mapClientes = new HashMap<>();
		for (Cliente cliente : allClientes) {
			String sexo = cliente.getSexo();
			Integer cantidadSexo = mapClientes.get(sexo);
			mapClientes.put(sexo , (null == cantidadSexo) ? 1 : cantidadSexo + 1);
		}
		return mapClientes;
	}
	
	public Map<String, Integer> buscarProductoMasVendido(Date from, Date to) {
		List<Venta> searchVenta = repository.getAllDate(from, to);
		Map<String, Integer> mapVenta = new HashMap<>();
		for (Venta venta : searchVenta) {
			List<Item> searchItem = venta.getItems();
			for (Item item : searchItem) {
				String producto = item.getNombreProducto() + '(' + item.getDescripcionProducto() + ')';
				Integer cantidadVentas = mapVenta.get(producto);
				mapVenta.put(producto , (null == cantidadVentas) ? item.getCantidad() : cantidadVentas + item.getCantidad());
			}		
		}
		return mapVenta;
	}
	
	public Map<String, BigDecimal> buscarTotalesDeCaja(Date from, Date to) {
		List<Movimiento> searchMovimientos = movimientosService.searchMovimientos(from, to, "");
		Map<String, BigDecimal> mapMovimientos = new HashMap<>();
		BigDecimal suma = new BigDecimal(0);
		for (Movimiento movimiento : searchMovimientos) {
			String categoria = movimiento.getCategoria();
			BigDecimal monto = mapMovimientos.get(categoria);			 			
			suma =  (monto == null) ? new BigDecimal(0) : mapMovimientos.get(categoria);
			suma =  (movimiento.getIsGasto())? suma.subtract(movimiento.getPrecio()) : suma.add(movimiento.getPrecio());		
			mapMovimientos.put(categoria, suma);
		}
		return mapMovimientos;
	}
	
	public Map<String, BigDecimal> buscarTurnosPorPeluqueroIngreso(Date from, Date to) {
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, BigDecimal> mapTurnos = new HashMap<>();
		BigDecimal suma = new BigDecimal(0);
		for (Turno turnos : searchTurnos) {
			Peluquero peluquero = turnos.getPeluquero();
			String nombreApellido = peluquero.getNombre() + ' ' + peluquero.getApellido();			
			BigDecimal montoPeluquero = mapTurnos.get(nombreApellido);			 			
			suma = (montoPeluquero == null) ? new BigDecimal(0) : mapTurnos.get(nombreApellido);
			suma = suma.add((turnos.getMontoCobro() == null) ? new BigDecimal(0) : turnos.getMontoCobro() );						
			mapTurnos.put(nombreApellido, suma);
		}
		return mapTurnos;
	}
	
	public Map<String, Integer> buscarTurnosPorPeluqueroCant(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTurnos = new HashMap<>();		
		for (Turno turnos : searchTurnos) {
			Peluquero peluquero = turnos.getPeluquero();
			String nombreApellido = peluquero.getNombre() + ' ' + peluquero.getApellido();			
			Integer cantidadTurnos = mapTurnos.get(nombreApellido);
			if (cantidadTurnos == null) {
				mapTurnos.put(nombreApellido, 1);
			} else {
				cantidadTurnos ++;
				mapTurnos.put(nombreApellido, cantidadTurnos);
			}
		}
		return mapTurnos;
	}	
	
	public TreeMap<String, Integer> buscarTurnoMasSolicitado(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTurnos = new HashMap<>();		
		
		for (String horario :  HORARIO_RANGO_DEFAULT.split(" ")) {				
			mapTurnos.put(horario, 0);			
		}		
		
		for (Turno turno : searchTurnos) {
			
			Date desde = turno.getDesde();
			Date hasta = turno.getHasta();	
			
			Calendar calendarDesde = Calendar.getInstance();
			calendarDesde.setTime(desde);
			Calendar calendarHasta = Calendar.getInstance();
			calendarHasta.setTime(hasta);
			int horaDesde = calendarDesde.get(Calendar.HOUR_OF_DAY);
			int minDesde = calendarDesde.get(Calendar.MINUTE);			
			int horaHasta = calendarHasta.get(Calendar.HOUR_OF_DAY);
			int minHasta = calendarHasta.get(Calendar.MINUTE);		
						
			String horaMinDesde = ordenarTiempo(horaDesde)+":"+ordenarTiempo(minDesde);
			String horaMinHasta = ordenarTiempo(horaHasta)+":"+ordenarTiempo(minHasta);
						
			for (int i=0; i<1;) {
				
				Integer cantidadTurnos = mapTurnos.get(horaMinDesde);
				cantidadTurnos ++;
				mapTurnos.put(horaMinDesde, cantidadTurnos);
				
				if (minDesde == 45) {
					minDesde = 0;
					horaDesde++;
				} else {
					minDesde = minDesde + 15;
				}			
				
				if (horaMinHasta.compareTo(horaMinDesde) == 0) {
					i=1;
				}
				
				horaMinDesde = ordenarTiempo(horaDesde)+":"+ordenarTiempo(minDesde);
			}
		}
		
		//Ordenar
		TreeMap<String, Integer> sorted = new TreeMap<>();		 
	    sorted.putAll(mapTurnos);
	    for (@SuppressWarnings("unused") Map.Entry<String, Integer> entry : sorted.entrySet()) ;
	
		return sorted;
	}
	
	public String ordenarTiempo(int tiempo) {
		if (tiempo < 10) {
			return "0"+String.valueOf(tiempo);
		} else {
			return String.valueOf(tiempo);
		}		
	}
	
	public EstadisticasRepository getRepository() {
		return repository;
	}

	public void setRepository(EstadisticasRepository repository) {
		this.repository = repository;
	}

	public ClientesService getClientesService() {
		return clientesService;
	}

	public void setClientesService(ClientesService clientesService) {
		this.clientesService = clientesService;
	}

	public MovimientosService getMovimientosService() {
		return movimientosService;
	}

	public void setMovimientosService(MovimientosService movimientosService) {
		this.movimientosService = movimientosService;
	}

	public TurnosService getTurnosService() {
		return turnosService;
	}

	public void setTurnosService(TurnosService turnosService) {
		this.turnosService = turnosService;
	}

	public VentaService getVentasService() {
		return ventasService;
	}

	public void setVentasService(VentaService ventasService) {
		this.ventasService = ventasService;
	}
	
}
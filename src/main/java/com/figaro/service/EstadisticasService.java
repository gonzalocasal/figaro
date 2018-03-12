package com.figaro.service;

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
	
	private static final String SIN_CIUDAD = "Sin Ciudad";

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
			if (null == ciudad)
				ciudad = SIN_CIUDAD;
			Integer cantidadHabitantes = mapClientes.get(ciudad);
			if (cantidadHabitantes == null) {
				mapClientes.put(ciudad, 1);
			} else {
				cantidadHabitantes ++;
				mapClientes.put(ciudad, cantidadHabitantes);
			}
		}
		return mapClientes;
	}
	
	public Map<String, Integer> buscarClienteSexo(){
		List<Cliente> allClientes = clientesService.getAllClientes();
		Map<String, Integer> mapClientes = new HashMap<>();
		for (Cliente cliente : allClientes) {
			String sexo = cliente.getSexo();
			Integer cantidadHabitantes = mapClientes.get(sexo);
			if (cantidadHabitantes == null) {
				mapClientes.put(sexo, 1);
			} else {
				cantidadHabitantes ++;
				mapClientes.put(sexo, cantidadHabitantes);
			}
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
				if (cantidadVentas == null) {
					mapVenta.put(producto, item.getCantidad());
				} else {
					cantidadVentas = cantidadVentas + item.getCantidad();
					mapVenta.put(producto, cantidadVentas);
				}
			}		
		}
		return mapVenta;
	}
	
	public Map<String, BigDecimal> buscarTotalesDeCaja(Date from, Date to) {

		String category = "";
		
		List<Movimiento> searchMovimientos = movimientosService.searchMovimientos(from, to, category);
		Map<String, BigDecimal> mapMovimientos = new HashMap<>();
		BigDecimal suma = new BigDecimal(0);
		for (Movimiento movimiento : searchMovimientos) {
			String categoria = movimiento.getCategoria();
			BigDecimal cantidadVentas = mapMovimientos.get(categoria);			 			
			if (cantidadVentas == null) {
				mapMovimientos.put(categoria, new BigDecimal(1));
				suma = new BigDecimal(0);
			}
			else 
				suma = mapMovimientos.get(categoria);
			BigDecimal precio = movimiento.getPrecio();
			if (movimiento.getIsGasto()) {
				suma = suma.subtract(precio);
			} else {
				suma = suma.add(precio);
			}			
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
			BigDecimal cantidadTurnos = mapTurnos.get(nombreApellido);			 			
			if (cantidadTurnos == null) {
				mapTurnos.put(nombreApellido, new BigDecimal(1));
				suma = new BigDecimal(0);
			}
			else 
				suma = mapTurnos.get(nombreApellido);
						
			BigDecimal precio = turnos.calculatePrecio();	
			
			suma = suma.add(precio);						
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
		
		String[] horarios = {"08:00","08:15","08:30","08:45","09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45","11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45","14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45","17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00","19:15","19:30","19:45","20:00","20:15","20:30","20:45","21:00"};		
		for (String horario : horarios) {				
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
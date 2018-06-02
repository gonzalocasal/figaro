package com.figaro.service;

import static com.figaro.util.Constants.HORARIO_RANGO_DEFAULT;
import static com.figaro.util.Constants.SIN_CIUDAD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.figaro.model.Cliente;
import com.figaro.model.Item;
import com.figaro.model.Movimiento;
import com.figaro.model.Empleado;
import com.figaro.model.Turno;
import com.figaro.model.Venta;
import com.figaro.repository.EstadisticasRepository;

public class EstadisticasService {

	final static Logger LOGGER = Logger.getLogger(EstadisticasService.class);
	public static final String MES_RANGO_DEFAULT = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31";
	public static final String SEMANA_RANGO_DEFAULT = "Lunes Martes Miercoles Jueves Viernes Sabado Domingo";
	public static final String WEEK_RANGO_DEFAULT = "1 2 3 4 5 6 7";
	
	
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
			if (null!=sexo) {
				Integer cantidadSexo = mapClientes.get(sexo);
				mapClientes.put(sexo , (null == cantidadSexo) ? 1 : cantidadSexo + 1);
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
				mapVenta.put(producto , (null == cantidadVentas) ? item.getCantidad() : cantidadVentas + item.getCantidad());
			}		
		}
		return mapVenta;
	}
	
	public Map<String, BigDecimal> buscarTotalesDeCaja(Date from, Date to) {
		List<Movimiento> searchMovimientos = movimientosService.searchMovimientos(from, to, "");
		Map<String, BigDecimal> mapMovimientos = new HashMap<>();
		for (Movimiento movimiento : searchMovimientos) {
			String categoria = movimiento.getCategoria();
			BigDecimal monto = mapMovimientos.get(categoria);			 			
			BigDecimal suma =  (monto == null) ? new BigDecimal(0) : monto;
			suma =  (movimiento.getIsGasto())? suma.subtract(movimiento.getPrecio()) : suma.add(movimiento.getPrecio());		
			mapMovimientos.put(categoria, suma);
		}
		return mapMovimientos;
	}
	
	public Map<String, BigDecimal> buscarTurnosPorEmpleadoIngreso(Date from, Date to) {
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, BigDecimal> mapTurnos = new HashMap<>();
		BigDecimal suma = new BigDecimal(0);
		for (Turno turno : searchTurnos) {
			Empleado empleado = turno.getEmpleado();
			String nombreApellido = empleado.getNombre() + ' ' + empleado.getApellido();			
			BigDecimal montoEmpleado = mapTurnos.get(nombreApellido);			 			
			suma = (montoEmpleado == null) ? new BigDecimal(0) : mapTurnos.get(nombreApellido);
			suma = suma.add((turno.getCobro() == null) ? new BigDecimal(0) : turno.getCobro().getPrecio() );						
			mapTurnos.put(nombreApellido, suma);
		}
		return mapTurnos;
	}
	
	public Map<String, Integer> buscarTurnosPorEmpleadoCant(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTurnos = new HashMap<>();		
		for (Turno turnos : searchTurnos) {
			Empleado empleado = turnos.getEmpleado();
			String nombreApellido = empleado.getNombre() + ' ' + empleado.getApellido();			
			Integer cantidadTurnos = mapTurnos.get(nombreApellido);
			mapTurnos.put(nombreApellido, (cantidadTurnos == null) ? 1: cantidadTurnos + 1);
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
				
				if (horaMinDesde.equals(horaMinHasta)) {
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
	
	public TreeMap<String, Integer> buscarTurnoMes(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTurnos = new HashMap<>();		
		
		for (String dia :  MES_RANGO_DEFAULT.split(" ")) {				
			mapTurnos.put(dia, 0);			
		}		
		
		for (Turno turno : searchTurnos) {
			
			Date desde = turno.getDesde();
			Calendar calendarDesde = Calendar.getInstance();
			calendarDesde.setTime(desde);			
			int horaDesde = calendarDesde.get(Calendar.DAY_OF_MONTH);		
			String diaDesde = String.valueOf(horaDesde);
				
			Integer cantidadTurnos = mapTurnos.get(diaDesde);
			cantidadTurnos ++;
			mapTurnos.put(diaDesde, cantidadTurnos);		
		
		}
		//Ordenar
		TreeMap<String, Integer> sorted = new TreeMap<>();		 
	    sorted.putAll(mapTurnos);
	    for (@SuppressWarnings("unused") Map.Entry<String, Integer> entry : sorted.entrySet()) ;
	
		return sorted;
	}
	
	public List<TreeMap<String, Integer>> buscarTurnoSexoMes(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTotal = new HashMap<>();
		Map<String, Integer> mapM = new HashMap<>();
		Map<String, Integer> mapH = new HashMap<>();	
		List<TreeMap<String,Integer>> resultado = new ArrayList<TreeMap<String,Integer>>();
		
		for (String dia :  MES_RANGO_DEFAULT.split(" ")) {				
			mapTotal.put(dia, 0);
			mapM.put(dia, 0);
			mapH.put(dia, 0);
		}		
						
		for (Turno turno : searchTurnos) {
			
			Date desde = turno.getDesde();
			Calendar calendarDesde = Calendar.getInstance();
			calendarDesde.setTime(desde);			
			int intDesde = calendarDesde.get(Calendar.DAY_OF_MONTH);		
			String diaDesde = String.valueOf(intDesde);
			//String diaDesde = (intDesde < 10 ? "0" : "") + intDesde;
			
			Integer cantidadTotal = mapTotal.get(diaDesde);
			cantidadTotal ++;
			mapTotal.put(diaDesde, cantidadTotal);			
			
			String sexo = turno.getCliente().getSexo();
			//String sexoCompara = "hombre";
			if ("hombre".equals(sexo)) {
				Integer cantidadH = mapH.get(diaDesde);
				cantidadH ++;
				mapH.put(diaDesde, cantidadH);
			} else {
				Integer cantidadM = mapM.get(diaDesde);
				cantidadM ++;
				mapM.put(diaDesde, cantidadM);
			}		
		}
		//Ordenar
		TreeMap<String, Integer> sortedTotal = new TreeMap<>();		 
		sortedTotal.putAll(mapTotal);
	    //for (@SuppressWarnings("unused") Map.Entry<String, Integer> entry : sortedTotal.entrySet()) ;	    
	    for (String dia :  MES_RANGO_DEFAULT.split(" ")) {				
	    	sortedTotal.put(dia, mapTotal.get(dia));	
	    	
		}
	
	    TreeMap<String, Integer> sortedH = new TreeMap<>();		 
	    sortedH.putAll(mapH);
	    //for (@SuppressWarnings("unused") Map.Entry<String, Integer> entry : sortedH.entrySet()) ;
	    for (String dia :  MES_RANGO_DEFAULT.split(" ")) {				
	    	sortedH.put(dia, mapH.get(dia));	
	    	
		}
	    
	    TreeMap<String, Integer> sortedM = new TreeMap<>();		 
	    sortedM.putAll(mapM);
	    //for (@SuppressWarnings("unused") Map.Entry<String, Integer> entry : sortedM.entrySet()) ;
	    for (String dia :  MES_RANGO_DEFAULT.split(" ")) {				
	    	sortedM.put(dia, mapM.get(dia));	
	    	
		}
	    
	    resultado.add(sortedTotal);
	    resultado.add(sortedH);
	    resultado.add(sortedM);
	    
		return resultado;
	}
	
	public LinkedHashMap<String,Integer> buscarTurnoSemana(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTurnos = new HashMap<>();		
		
		for (String dia :  WEEK_RANGO_DEFAULT.split(" ")) {				
			mapTurnos.put(dia, 0);			
		}		
		
		for (Turno turno : searchTurnos) {
			
			Date desde = turno.getDesde();
			Calendar calendarDesde = Calendar.getInstance();
			calendarDesde.setTime(desde);			
			int horaDesde = calendarDesde.get(Calendar.DAY_OF_WEEK);	
			if (horaDesde == 1) {
				horaDesde = 7;
			} else {				
				horaDesde = horaDesde -1;
			}
				
			String diaDesde = String.valueOf(horaDesde);
			
			Integer cantidadTurnos = mapTurnos.get(diaDesde);
			cantidadTurnos ++;
			mapTurnos.put(diaDesde, cantidadTurnos);		
		
		}
		int i=0;
		for (String dia :  SEMANA_RANGO_DEFAULT.split(" ")) {
			i++;
			String iaux = String.valueOf(i);
			mapTurnos.put(dia,mapTurnos.remove(iaux));
		}	
		//Ordenar
		LinkedHashMap<String, Integer> sorted = new LinkedHashMap<String, Integer>();
	    for (String dia :  SEMANA_RANGO_DEFAULT.split(" ")) {				
	    	sorted.put(dia, mapTurnos.get(dia));	
	    	
		}	
	    
		return sorted;
	}
	
	public List<LinkedHashMap<String, Integer>> buscarTurnoSexoSemana(Date from, Date to) {
		
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTotal = new HashMap<>();
		Map<String, Integer> mapM = new HashMap<>();
		Map<String, Integer> mapH = new HashMap<>();	
		List<LinkedHashMap<String, Integer>> resultado = new ArrayList<LinkedHashMap<String, Integer>>();
		
		for (String dia :  WEEK_RANGO_DEFAULT.split(" ")) {				
			mapTotal.put(dia, 0);
			mapM.put(dia, 0);
			mapH.put(dia, 0);
		}		
						
		for (Turno turno : searchTurnos) {
			
			Date desde = turno.getDesde();
			Calendar calendarDesde = Calendar.getInstance();
			calendarDesde.setTime(desde);			
			int intDesde = calendarDesde.get(Calendar.DAY_OF_WEEK);					
			if (intDesde == 1) {
				intDesde = 7;
			} else {				
				intDesde = intDesde -1;
			}
			String diaDesde = String.valueOf(intDesde);
			
			Integer cantidadTotal = mapTotal.get(diaDesde);
			cantidadTotal ++;
			mapTotal.put(diaDesde, cantidadTotal);			
			
			String sexo = turno.getCliente().getSexo();
			//String sexoCompara = "hombre";			
			if ("hombre".equals(sexo)) {
				Integer cantidadH = mapH.get(diaDesde);
				cantidadH ++;
				mapH.put(diaDesde, cantidadH);
			} else {
				Integer cantidadM = mapM.get(diaDesde);
				cantidadM ++;
				mapM.put(diaDesde, cantidadM);
			}		
		}
		
		int i=0;
		for (String dia :  SEMANA_RANGO_DEFAULT.split(" ")) {
			i++;
			String iaux = String.valueOf(i);
			mapTotal.put(dia,mapTotal.remove(iaux));
			mapH.put(dia,mapH.remove(iaux));
			mapM.put(dia,mapM.remove(iaux));
		}	
		
		//Ordenar
		LinkedHashMap<String, Integer> sortedTotal = new LinkedHashMap<String, Integer>();
	    for (String dia :  SEMANA_RANGO_DEFAULT.split(" ")) {				
	    	sortedTotal.put(dia, mapTotal.get(dia));	
	    	
		}	
	    LinkedHashMap<String, Integer> sortedH = new LinkedHashMap<String, Integer>();
	    for (String dia :  SEMANA_RANGO_DEFAULT.split(" ")) {				
	    	sortedH.put(dia, mapH.get(dia));	
	    	
		}	
	    LinkedHashMap<String, Integer> sortedM = new LinkedHashMap<String, Integer>();
	    for (String dia :  SEMANA_RANGO_DEFAULT.split(" ")) {				
	    	sortedM.put(dia, mapM.get(dia));	
	    	
		}	
	    
	    resultado.add(sortedTotal);
	    resultado.add(sortedH);
	    resultado.add(sortedM);
	    
		return resultado;
	}
	
	public Map<String, Integer> buscarTrabajoMasRequerido(Date from, Date to) {
		List<Turno> searchTurnos = repository.searchBetween (from,to);
		Map<String, Integer> mapTurnos = new HashMap<>();
		for (Turno turno : searchTurnos) {
			String trabajos = turno.getDescripcionTrabajos();
			for (String trabajo :  trabajos.split(" ")) {				
		    	Integer cantidadTrabajaos = mapTurnos.get(trabajo);
		    	mapTurnos.put(trabajo, (null == cantidadTrabajaos) ? 1 : cantidadTrabajaos + 1);
			}
		}
		return mapTurnos;
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
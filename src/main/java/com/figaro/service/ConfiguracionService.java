
package com.figaro.service;

import static com.figaro.util.Constants.HORARIO_FORMATO;
import static com.figaro.util.Constants.HORARIO_INTERVALO;
import static com.figaro.util.Constants.HORARIO_RANGO_DEFAULT;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;

import com.figaro.exception.HorarioInvalidoException;
import com.figaro.model.Categoria;
import com.figaro.model.Ciudad;
import com.figaro.model.Horario;
import com.figaro.model.Servicio;
import com.figaro.repository.ConfiguracionRepository;

public class ConfiguracionService {
	

	final static Logger LOGGER = Logger.getLogger(ConfiguracionService.class);

	private ConfiguracionRepository repository;
	
	private Preferences preferencias;

	public ConfiguracionService() {
		this.setPreferencias(Preferences.userNodeForPackage(this.getClass()));
	}
	
	//CIUDADES
	public Integer saveCiudad(Ciudad ciudad) {
		LOGGER.info("Guardando la ciudad: "+ ciudad.getNombre());
		return repository.saveCiudad(ciudad);
	}	
	
	public void deleteCiudad(Integer idCiudad) {
		repository.deleteCiudad(idCiudad);
	}
	
	public List<Ciudad> getCiudades() {
		LOGGER.info("Obteniendo todas las ciudades");
		return repository.getCiudades();
	}
	
	//SERVICIOS
	public Integer saveServicio(Servicio servicio) {
		LOGGER.info("Guardando el servicio: "+ servicio.getDescripcion() +" "+ servicio.getPrecio());
		return repository.saveServicio(servicio);
	}
	
	public void deleteServicio(Integer idServicio) {
		repository.deleteServicio(idServicio);		
	}
	
	public Servicio getServicio(Integer idServicio) {
		LOGGER.debug("Obteniendo el servicio con ID: " + idServicio);
		return repository.getServicio(idServicio);
	}

	public List<Servicio> getServicio() {
		LOGGER.debug("Obteniendo todos los servicios");
		return repository.getServicios();
	}

	public Servicio updateServicio(Servicio servicio) {
		LOGGER.info("Actualizando el servicio: "+ servicio.getDescripcion() +" "+ servicio.getPrecio());
		Servicio repoServicio = getServicio(servicio.getId());
		repoServicio.setDescripcion(servicio.getDescripcion());
		repoServicio.setPrecio(servicio.getPrecio());
		saveServicio(repoServicio);
		return repoServicio;
	}
	
	public List<Servicio> buscarTrabajos(String search) {
		LOGGER.debug("Buscando servicios: "+ search);
		return repository.buscar(search);
	}
	
	//CATEGORIAS
	public List<Categoria> getCategorias() {
		LOGGER.debug("Obteniendo todas las categorias");
		return repository.getCategorias();
	}

	public Integer save(Categoria categoria) {
		LOGGER.info("Guardando la categoria: "+ categoria.getNombre());
		return repository.saveCategoria(categoria);
	}

	public void deleteCategoria(Integer idCategoria) {
		repository.deleteCategoria(idCategoria);
	}
	
	//HORARIO
	public Horario getHorario() {
		Horario horario = repository.getHorario();
		List<String> rangoUsuario = generateRangoUsuario(horario);
		horario.setRango(Arrays.asList(HORARIO_RANGO_DEFAULT.split(" ")));
		horario.setRangoUsuario(rangoUsuario);
		return horario;
	}
	
	public Horario updateHorario(Horario nuevoHorario) {
		validateHorario(nuevoHorario);
		List<String> rangoUsuario = generateRangoUsuario(nuevoHorario);
		nuevoHorario.setRangoUsuario(rangoUsuario);
		repository.updateHorario(nuevoHorario);
		return nuevoHorario;
	}

	private List<String> generateRangoUsuario(Horario nuevoHorario) {
		List<String> rangoUsuario = new ArrayList<>();
		if(nuevoHorario.getCorrido())
			rangoUsuario.addAll(calculateRangoUsuario(nuevoHorario.getDesde(), nuevoHorario.getHasta()));
		else {
			rangoUsuario.addAll(calculateRangoUsuario(nuevoHorario.getDesde(), nuevoHorario.getHasta()));
			rangoUsuario.addAll(calculateRangoUsuario(nuevoHorario.getDesdeTarde(), nuevoHorario.getHastaTarde()));
		}
		return rangoUsuario;
	}

	private List<String>  calculateRangoUsuario (String desde, String hasta) {
		DateFormat sdf = new SimpleDateFormat(HORARIO_FORMATO);
		List<String> rango = new ArrayList<>();
		Date desdeDate = parseTime(desde);
		Date hastaDate = parseTime(hasta);
		Calendar cal = Calendar.getInstance();
		cal.setTime(desdeDate);
		while (cal.getTime().compareTo(hastaDate) < 1)  {
		    String time = sdf.format(cal.getTime());
		    cal.add(Calendar.MINUTE, HORARIO_INTERVALO);
		    rango.add(time);
		}
		return rango;
	}
	
	private Date parseTime (String time) {
		DateFormat sdf = new SimpleDateFormat(HORARIO_FORMATO);
		Date timeParsed = null;
		try {
			timeParsed = sdf.parse(time);
		} catch (ParseException e) { LOGGER.info("Se produjo un error al generar el rango de horarios"); }
		return timeParsed;
	}
	
	
	private void validateHorario(Horario nuevoHorario) {
		Date desdeDate = parseTime(nuevoHorario.getDesde());
		Date hastaDate = parseTime(nuevoHorario.getHasta());
		Date desdeTardeDate = parseTime(nuevoHorario.getDesdeTarde());
		Date hastaTardeDate = parseTime(nuevoHorario.getHastaTarde());
		Set<Date> dates = new HashSet<>(Arrays.asList(desdeDate, hastaDate,desdeTardeDate,hastaTardeDate));
		
		if (dates.size()<4)
			throw new HorarioInvalidoException ("");
		
		if (hastaDate.before(desdeDate) || hastaTardeDate.before(desdeTardeDate) )
			throw new HorarioInvalidoException ("");
	
		if (desdeDate.after(desdeTardeDate) || desdeDate.after(hastaTardeDate)  || hastaDate.after(desdeTardeDate) || hastaDate.after(hastaTardeDate)) 
			throw new HorarioInvalidoException ("");
	}
	
	public ConfiguracionRepository getRepository() {
		return repository;
	}
	public void setRepository(ConfiguracionRepository repository) {
		this.repository = repository;
	}
	public Preferences getPreferencias() {
		return preferencias;
	}
	public void setPreferencias(Preferences preferencias) {
		this.preferencias = preferencias;
	}

}
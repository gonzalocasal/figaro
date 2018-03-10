
package com.figaro.service;


import java.util.List;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;

import com.figaro.model.Categoria;
import com.figaro.model.Ciudad;
import com.figaro.model.Servicio;
import com.figaro.repository.ConfiguracionRepository;

public class ConfiguracionService {
	
	final static Logger LOGGER = Logger.getLogger(ConfiguracionService.class);

	private ConfiguracionRepository repository;
	
	private Preferences prefs;

	public ConfiguracionService() {
		this.prefs = Preferences.userNodeForPackage(this.getClass());
	}
	
	
	//CIUDADES
	public Integer saveCiudad(Ciudad ciudad) {
		LOGGER.info("Guardando la ciudad: "+ ciudad.getNombre());
		prefs.put("CIUDAD", ciudad.getNombre());
		return repository.saveCiudad(ciudad);
	}	
	
	public void deleteCiudad(Integer idCiudad) {
		repository.deleteCiudad(idCiudad);
	}
	
	public List<Ciudad> getCiudades() {
		LOGGER.info("Obteniendo todas las ciudades");
		LOGGER.info(prefs.get("CIUDAD", "fruta"));
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
	
	
	public ConfiguracionRepository getRepository() {
		return repository;
	}

	public void setRepository(ConfiguracionRepository repository) {
		this.repository = repository;
	}

	public Preferences getPrefs() {
		return prefs;
	}

	public void setPrefs(Preferences prefs) {
		this.prefs = prefs;
	}

}
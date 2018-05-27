package com.figaro.service;

import java.util.Date;
import java.util.List;

import static com.figaro.util.Constants.*;
import org.apache.log4j.Logger;

import com.figaro.model.Notificacion;
import com.figaro.model.Producto;
import com.figaro.repository.NotificacionesRepository;

public class NotificacionesService {

	final static Logger LOGGER = Logger.getLogger(NotificacionesService.class);
	
	private NotificacionesRepository repository;

	
	public List<Notificacion> getNotificaciones (int index){
		return repository.getNotificaciones(index);
	}


	public List<Notificacion> getAll (){
		return repository.getAll();
	}
	
	public void delete(int notificacionId) {
		repository.delete(notificacionId);
	}

	
	public void deleteAll() {
		repository.deleteAll();
	}
	
	public Integer getCantidadTotal() {
		return repository.getCantidadTotal();
	}
	
	public Integer getCantidadSinLeer() {
		return repository.getCantidadSinLeer();
	}
	
	
	public void leerTodas() {
		List<Notificacion> notificaciones = getAll();
		for (Notificacion n : notificaciones) {
			n.setLeida(true);
			repository.update(n);
		}
	}
	
	
	public void generarNotificacionStockMinimo(Producto producto) {
		Notificacion notificacion = new Notificacion();
		notificacion.setFecha(new Date());
		notificacion.setDetalle("Se alcanzó el stock minimo del producto " + producto.getNombre());
		notificacion.setLeida(false);
		notificacion.setCategoria(CATEGORIA_NOTIFICACION_STOCK);
		repository.save(notificacion);
		
	}

	public void setRepository(NotificacionesRepository repository) {
		this.repository = repository;
	}


	
}

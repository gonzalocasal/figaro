package com.figaro.controllerREST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.service.NotificacionesService;

@RestController
@RequestMapping(value = "/rest/")
public class NotificacionesControllerREST {
	
	@Autowired
	@Qualifier("NotificacionesServiceTransactional")
	private NotificacionesService service;
	
	
	@GetMapping("notificaciones")
    public ResponseEntity<?> getNotificaciones( @RequestParam int index) {
		return new ResponseEntity<>(service.getNotificaciones(index), HttpStatus.OK);
    }
	
	@PutMapping("notificaciones/leer")
    public ResponseEntity<?> leerTodas() {
		service.leerTodas();
		return new ResponseEntity<>( HttpStatus.OK);
    }

	@GetMapping("notificaciones/cantidad-total")
    public ResponseEntity<?> getCantidadTotal() {
		return new ResponseEntity<>(service.getCantidadTotal(), HttpStatus.OK);
    }

	
	@GetMapping("notificaciones/cantidad")
    public ResponseEntity<?> getCantidad() {
		return new ResponseEntity<>(service.getCantidadSinLeer(), HttpStatus.OK);
    }

}
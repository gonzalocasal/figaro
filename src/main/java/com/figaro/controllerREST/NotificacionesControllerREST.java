package com.figaro.controllerREST;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/notificaciones")
public class NotificacionesControllerREST {
	
	
	@GetMapping("/cantidad")
    public ResponseEntity<?> getCantidad() {
		return new ResponseEntity<>(11, HttpStatus.OK);
    }

}
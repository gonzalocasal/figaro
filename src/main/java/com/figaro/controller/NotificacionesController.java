package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotificacionesController {
	
	@RequestMapping("/notificaciones")
	public String turnos() {
		return "notificaciones/notificaciones";
	}
}

package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TurnosController {

	@RequestMapping("/")
	public String turnos() {
		return "turnos/turnos";
	}


	@RequestMapping("/turnos/buscar")
	public String turnosBuscar() {
		return "turnos/buscar";
	}

	
}

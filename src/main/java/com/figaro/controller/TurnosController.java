package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TurnosController {

	@RequestMapping("/")
	public String turnos() {
		return "html/turnos/turnos";
	}

	@RequestMapping("/turnos/cliente/{clienteId}")
	public String turnosDeCliente() {
		return "html/turnos/turnos-cliente";
	}

	@RequestMapping("/turnos/peluquero/{peluqueroId}")
	public String turnosDePeluquero() {
		return "html/turnos/turnos-peluquero";
	}

	@RequestMapping("/turnos/peluquero/{peluqueroId}/sinpagar")
	public String turnosDePeluqueroSinPagar() {
		return "html/turnos/turnos-peluquero";
	}

	
	
}

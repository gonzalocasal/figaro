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

	@RequestMapping("/turnos/empleados/{empleadoId}")
	public String turnosDeEmpleado() {
		return "html/turnos/turnos-empleado";
	}

	@RequestMapping("/turnos/empleados/{empleadoId}/sinpagar")
	public String turnosDeEmpleadoSinPagar() {
		return "html/turnos/turnos-empleado";
	}

	
	
}

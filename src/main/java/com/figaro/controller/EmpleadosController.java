package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmpleadosController {
	
	@RequestMapping("/empleados")
	public String caja() {
		return "empleados/empleados";
	}
	
}

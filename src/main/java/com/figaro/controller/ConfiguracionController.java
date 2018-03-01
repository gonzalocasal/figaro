package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ConfiguracionController {
	
	@RequestMapping("/configuracion")
	public String turnos() {
		return "html/configuracion/configuracion";
	}
}

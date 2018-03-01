package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EstadisticasController {
	
	@RequestMapping("/estadisticas")
	public String estadisticas() {
		return "html/estadisticas/estadisticas";
	}

}

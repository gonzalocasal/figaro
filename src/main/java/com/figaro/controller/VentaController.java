package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VentaController {
	
	@RequestMapping("/venta")
	public String venta() {
		return "html/venta/venta";
	}

	@RequestMapping("/venta/historial-venta")
	public String historialVenta() {
		return "html/venta/historial-venta";
	}

}

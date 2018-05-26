package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProveedoresController {
	
	@RequestMapping("/proveedores")
	public String cliente() {
		return "proveedores/proveedores";
	}

}

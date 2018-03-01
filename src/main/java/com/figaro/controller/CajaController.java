package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CajaController {
	
	@RequestMapping("/peluqueros")
	public String caja() {
		return "html/peluqueros/peluqueros";
	}

}

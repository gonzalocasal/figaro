package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CajaController {
	
	@RequestMapping("/caja")
	public String caja() {
		return "caja/caja";
	}

}

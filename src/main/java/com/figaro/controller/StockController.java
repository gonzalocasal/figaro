package com.figaro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StockController {
	
	@RequestMapping("/stock")
	public String stock() {
		return "html/stock/stock";
	}
	
	/*
	@RequestMapping("/stock/ventaProducto")
	public String ventaProducto() {
		return "html/stock/ventaProducto";
	}
	*/

}

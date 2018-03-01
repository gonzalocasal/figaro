package com.figaro.dto;

import com.figaro.model.Movimiento;
import com.figaro.model.Venta;

public class VentaDTO {

	private Venta venta;
	private Movimiento movimiento;
	public Venta getVenta() {
		return venta;
	}
	
	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}
	
}


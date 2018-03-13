package com.figaro.dto;

import java.math.BigDecimal;

public class TotalesEmpleadoDTO {
	
	private Integer turnos;
	private BigDecimal pago;
	
	public TotalesEmpleadoDTO(Integer turnos, BigDecimal pago) {
		this.turnos = turnos;
		this.pago = pago;
	}

	public Integer getTurnos() {
		return turnos;
	}
	public void setTurnos(Integer turnos) {
		this.turnos = turnos;
	}
	public BigDecimal getPago() {
		return pago;
	}
	public void setPago(BigDecimal pago) {
		this.pago = pago;
	}
	
	
}

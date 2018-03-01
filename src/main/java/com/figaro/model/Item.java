package com.figaro.model;

import java.math.BigDecimal;

public class Item{

	private Integer id;
	private String nombreProducto;
	private String descripcionProducto;
	private BigDecimal precioUnitario;
	private Integer cantidad;
	private BigDecimal precioTotal;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}
	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	public void inicializar() {
		this.setCantidad(0);
		this.setPrecioUnitario(new BigDecimal (0.00));
		this.setPrecioTotal(new BigDecimal (0.00));
	}
	
}

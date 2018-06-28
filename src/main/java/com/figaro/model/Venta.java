package com.figaro.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Venta {

	private int id;
	private BigDecimal precio;
	private Date fecha;
	private List<Item> items;
	private Movimiento cobroVenta;
	
	public void update(Venta venta) {
		this.precio = venta.getPrecio();
		this.fecha 	= venta.getFecha();
		this.items	= venta.getItems();
		this.cobroVenta = venta.getCobroVenta();
	}
	
	public int getId() {
		return id;
	}
	
	public String getIdToString() {
		return String.valueOf(id);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}
	
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}

	
	public Movimiento getCobroVenta() {
		return cobroVenta;
	}
	public void setCobroVenta(Movimiento cobroVenta) {
		this.cobroVenta = cobroVenta;
	}
	

	@Override
	public String toString() {
		return "Venta [id=" + id + ", precio=" + precio + ", fecha=" + fecha + "]";
	}
}

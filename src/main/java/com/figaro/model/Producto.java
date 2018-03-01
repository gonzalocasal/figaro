package com.figaro.model;

import java.math.BigDecimal;

public class Producto {

	private int id;
	private String nombre;
	private String descripcion;
	private int cantidad;
	private int cantidadMinima;
	private BigDecimal precio; 
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}	

	public int getCantidadMinima() {
		return cantidadMinima;
	}
	public void setCantidadMinima(int cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public BigDecimal getPrecio() {
		return precio;
	}
	
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
		
	public Producto update(Producto producto) {
		setNombre(producto.getNombre());
		setDescripcion(producto.getDescripcion());
		setCantidad(producto.getCantidad());
		setCantidadMinima(producto.getCantidadMinima());
		setPrecio(producto.getPrecio());
		return this;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", cantidadMinima=" + cantidadMinima + ", precio=" + precio + "]";
	}
	

	
}

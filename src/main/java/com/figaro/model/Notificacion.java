package com.figaro.model;

import java.util.Date;

public class Notificacion {

	private Integer id;
	private String categoria;
	private String detalle;
	private Boolean leida;
	private Date fecha;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Boolean getLeida() {
		return leida;
	}
	public void setLeida(Boolean leida) {
		this.leida = leida;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}

package com.figaro.model;

import static com.figaro.util.Constants.DATE_TIME_FORMAT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Turno {

	private int id;
	private Cliente cliente;
	private Peluquero peluquero;
	@JsonFormat(pattern=DATE_TIME_FORMAT)
	private Date desde;
	@JsonFormat(pattern=DATE_TIME_FORMAT)
	private Date hasta;
	private Boolean cobrado;
	private Boolean pagado;
	private Movimiento cobro;
	private Movimiento pago;
	private Set<Trabajo> trabajos;
	
	public Turno () {
		this.cobrado = false;
		this.pagado = false;
		this.trabajos = new HashSet<>();
	}
	
	public void update(Turno turno) {
		this.cliente = turno.getCliente();
		this.peluquero = turno.getPeluquero();
		this.desde = turno.getDesde();
		this.hasta = turno.getHasta();
		this.cobrado = turno.getCobrado();
		this.cobro = turno.getCobro();
		this.trabajos.removeAll(new ArrayList<Trabajo>(this.trabajos));
		this.trabajos.addAll(turno.getTrabajos());
		this.cobro = turno.getCobro();
		this.pago =  turno.getPago();
		if(this.cobro != null) 
			this.cobro.setId(null);
		if(this.pago != null) 
			this.pago.setId(null);
		
	}
	
	public BigDecimal calculatePrecio(){
		BigDecimal precio = new BigDecimal(0);
		for (Trabajo t : this.getTrabajos())
			precio = precio.add(t.getServicio().getPrecio());
		return precio;
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Peluquero getPeluquero() {
		return peluquero;
	}
	public void setPeluquero(Peluquero peluquero) {
		this.peluquero = peluquero;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Boolean getCobrado() {
		return cobrado;
	}
	public void setCobrado(Boolean cobrado) {
		this.cobrado = cobrado;
	}
	public Set<Trabajo> getTrabajos() {
		return trabajos;
	}
	public void setTrabajos(Set<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}
	public Date getHasta() {
		return hasta;
	}
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
	public Date getDesde() {
		return desde;
	}
	public void setDesde(Date desde) {
		this.desde = desde;
	}
	public Movimiento getCobro() {
		return cobro;
	}
	public void setCobro(Movimiento movimiento) {
		this.cobro = movimiento;
	}
	public Boolean getPagado() {
		return pagado;
	}
	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}
	public Movimiento getPago() {
		return pago;
	}
	public void setPago(Movimiento pago) {
		this.pago = pago;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turno other = (Turno) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Turno [id=" + id + ", cliente=" + cliente + ", peluquero=" + peluquero + ", desde=" + desde + ", hasta=" + hasta + ", cobrado=" + cobrado + ", movimiento=" + cobro + ", trabajos=" + trabajos + "]";
	}

}
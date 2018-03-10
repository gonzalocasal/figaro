package com.figaro.dto;

import static com.figaro.util.Constants.DATE_TIME_FORMAT;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.figaro.model.Cliente;
import com.figaro.model.Movimiento;
import com.figaro.model.Peluquero;
import com.figaro.model.Turno;

public class TurnoDTO {

	
	private int id;
	private Cliente cliente;
	private Peluquero peluquero;
	private Boolean cobrado;
	private Boolean pagado;
	private Movimiento cobro;
	private Movimiento pago;
	private BigDecimal montoCobro;
	private BigDecimal montoPago;
	private String descripcionTrabajos;
	@JsonFormat(pattern=DATE_TIME_FORMAT)
	private Date desde;
	@JsonFormat(pattern=DATE_TIME_FORMAT)
	private Date hasta;
	
	
	
	public  TurnoDTO(Turno turno) {
		this.id = turno.getId();
		this.cliente = turno.getCliente();
		this.peluquero = turno.getPeluquero();
		this.desde = turno.getDesde();
		this.hasta = turno.getHasta();
		this.cobrado = turno.getCobrado();
		this.cobro = turno.getCobro();
		this.pagado = turno.getPagado();
		this.pago =  turno.getPago();
		this.montoCobro = turno.getMontoCobro();
		this.montoPago = turno.getMontoPago();
		this.descripcionTrabajos = turno.getDescripcionTrabajos();
		
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public Boolean getCobrado() {
		return cobrado;
	}


	public void setCobrado(Boolean cobrado) {
		this.cobrado = cobrado;
	}


	public Boolean getPagado() {
		return pagado;
	}


	public void setPagado(Boolean pagado) {
		this.pagado = pagado;
	}


	public Movimiento getCobro() {
		return cobro;
	}


	public void setCobro(Movimiento cobro) {
		this.cobro = cobro;
	}


	public Movimiento getPago() {
		return pago;
	}


	public void setPago(Movimiento pago) {
		this.pago = pago;
	}


	public BigDecimal getMontoCobro() {
		return montoCobro;
	}


	public void setMontoCobro(BigDecimal montoCobro) {
		this.montoCobro = montoCobro;
	}


	public BigDecimal getMontoPago() {
		return montoPago;
	}


	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}


	public String getDescripcionTrabajos() {
		return descripcionTrabajos;
	}


	public void setDescripcionTrabajos(String descripcionTrabajos) {
		this.descripcionTrabajos = descripcionTrabajos;
	}


	public Date getDesde() {
		return desde;
	}


	public void setDesde(Date desde) {
		this.desde = desde;
	}


	public Date getHasta() {
		return hasta;
	}


	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}

	
	
}

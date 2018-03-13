package com.figaro.model;

import java.util.Date;

public class Cliente extends Persona {
	
	private Date ultimaVisita;
	private String notas;
	
	public Cliente() {
	}

	public Date getUltimaVisita() {
		return ultimaVisita;
	}

	public void setUltimaVisita(Date ultimaVisita) {
		this.ultimaVisita = ultimaVisita;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}
	
	public void update(Cliente cliente) {
		this.setNombre(cliente.getNombre());
		this.setApellido(cliente.getApellido());
		this.setEmail(cliente.getEmail());
		this.setSexo(cliente.getSexo());
		this.setTelefono(cliente.getTelefono());
		this.setDirCiudad(cliente.getDirCiudad());
		this.setDirCalle(cliente.getDirCalle());
		this.setDirNumeroCalle(cliente.getDirNumeroCalle());
		this.setDirPiso(cliente.getDirPiso());
		this.setDirDpto(cliente.getDirDpto());
		this.setNotas(cliente.getNotas());
	}

	

}

package com.figaro.model;

import java.util.HashSet;
import java.util.Set;

public class Peluquero extends Persona {
	
	private Set<Trabajo> trabajos;
	private Boolean habilitado;
	
	public Peluquero() {
		this.habilitado = true;
		this.trabajos = new HashSet<Trabajo>();
	}
	

	public void update(Peluquero peluquero) {
		this.setNombre(peluquero.getNombre());
		this.setApellido(peluquero.getApellido());
		this.setEmail(peluquero.getEmail());
		this.setSexo(peluquero.getSexo());
		this.setTelefono(peluquero.getTelefono());
		this.setDirCiudad(peluquero.getDirCiudad());
		this.setDirCalle(peluquero.getDirCalle());
		this.setDirNumeroCalle(peluquero.getDirNumeroCalle());
		this.setDirPiso(peluquero.getDirPiso());
		this.setDirDpto(peluquero.getDirDpto());
		for(Trabajo trabajo : peluquero.getTrabajos()) {
			trabajo.setId(null);
		}
		this.getTrabajos().removeAll(this.getTrabajos());
		this.getTrabajos().addAll(peluquero.getTrabajos());
	}
	
	public Set<Trabajo> getTrabajos() {
		return trabajos;
	}

	public void setTrabajos(Set<Trabajo> trabajos) {
		this.trabajos = trabajos;
	}


	public Boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}


	
	
}

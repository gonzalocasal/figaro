package com.figaro.model;

import java.util.HashSet;
import java.util.Set;

public class Empleado extends Persona {
	
	private Set<Trabajo> trabajos;
	private Set<Integer> diasDisponible;
	private Boolean habilitado;
	
	public Empleado() {
		this.habilitado = true;
		this.trabajos = new HashSet<Trabajo>();
		this.diasDisponible = new HashSet<Integer>();
	}
	

	public void update(Empleado empleado) {
		this.setNombre(empleado.getNombre());
		this.setApellido(empleado.getApellido());
		this.setEmail(empleado.getEmail());
		this.setSexo(empleado.getSexo());
		this.setNacimiento(empleado.getNacimiento());
		this.setTelefono(empleado.getTelefono());
		this.setDirCiudad(empleado.getDirCiudad());
		this.setDirCalle(empleado.getDirCalle());
		this.setDirNumeroCalle(empleado.getDirNumeroCalle());
		this.setDirPiso(empleado.getDirPiso());
		this.setDirDpto(empleado.getDirDpto());
		for(Trabajo trabajo : empleado.getTrabajos()) {
			trabajo.setId(null);
		}
		this.getTrabajos().removeAll(this.getTrabajos());
		this.getTrabajos().addAll(empleado.getTrabajos());
		this.getDiasDisponible().removeAll(this.getDiasDisponible());
		this.getDiasDisponible().addAll(empleado.getDiasDisponible());
		
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


	public Set<Integer> getDiasDisponible() {
		return diasDisponible;
	}


	public void setDiasDisponible(Set<Integer> diasDisponible) {
		this.diasDisponible = diasDisponible;
	}


	
	
}

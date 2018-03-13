package com.figaro.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.figaro.dto.TotalesEmpleadoDTO;
import com.figaro.model.Empleado;
import com.figaro.repository.EmpleadosRepository;
import com.figaro.repository.TurnosRepository;

public class EmpleadosService {
	
	final static Logger LOGGER = Logger.getLogger(EmpleadosService.class);

	private EmpleadosRepository repository;
	
	private TurnosRepository turnosRepository;
	
	public Empleado getEmpleado(int empleadoId) {
		LOGGER.debug("Obteniendo el empleado con ID: " + empleadoId);
		return repository.getEmpleado(empleadoId);
	}
	
	public Integer saveEmpleado(Empleado empleado) {
		LOGGER.info("Guardando el nuevo empleado: "+ empleado.getNombre());
		return repository.saveEmpleado(empleado);
	}
	
	public Empleado updateEmpleado(Empleado empleado) {
		LOGGER.info("Actualizando empleado " + empleado.toString());
		Empleado updated = getEmpleado(empleado.getId());
		updated.update(empleado);
		repository.updateEmpleado(updated);
		return updated;
	}

	public void habilitarEmpleado(Integer empleadoId) {
		Empleado empleado = getEmpleado(empleadoId);
		LOGGER.info("Habilitando empleado " + empleado.toString());
		empleado.setHabilitado(!empleado.isHabilitado());
		repository.updateEmpleado(empleado);
		
	}
	
	public TotalesEmpleadoDTO getTotalesEmpleado(int empleadoId) {
		LOGGER.info("Obteniendo los totales del empleado con ID: " +  empleadoId);
		Integer turnos = turnosRepository.getCantidadTurnosEmpleado(empleadoId);
		BigDecimal pago = turnosRepository.getTotalPagosTurnosEmpleado(empleadoId);
		return new TotalesEmpleadoDTO(turnos, pago);
	}

	
	public void deleteEmpleado(Integer empleadoId) {
		LOGGER.info("Eliminando el empleado con ID" + empleadoId);
		repository.deleteEmpleado(empleadoId);
	}
	
	public List<Empleado> getEmpleados() {
		LOGGER.info("Obteniendo todos los empleados");
		return repository.getEmpleados();
	}

	public List<Empleado> getEmpleadosHabilitados() {
		List<Empleado> empleados = getEmpleados();
		return empleados.stream().filter(e -> e.isHabilitado()).collect(Collectors.toList());
	}

	public void setTurnosRepository(TurnosRepository turnosRepository) {
		this.turnosRepository = turnosRepository;
	}

	public void setRepository(EmpleadosRepository repository) {
		this.repository = repository;
	}
	

}

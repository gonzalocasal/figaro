package com.figaro.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.figaro.dto.TotalesPeluqueroDTO;
import com.figaro.model.Peluquero;
import com.figaro.repository.PeluquerosRepository;
import com.figaro.repository.TurnosRepository;

public class PeluquerosService {
	
	final static Logger LOGGER = Logger.getLogger(PeluquerosService.class);

	private PeluquerosRepository repository;
	
	private TurnosRepository turnosRepository;
	
	public Peluquero getPeluquero(int peluqueroId) {
		LOGGER.debug("Obteniendo el peluquero con ID: " + peluqueroId);
		return repository.getPeluquero(peluqueroId);
	}
	
	public Integer savePeluquero(Peluquero peluquero) {
		LOGGER.info("Guardando el nuevo peluquero: "+ peluquero.getNombre());
		return repository.savePeluquero(peluquero);
	}
	
	public Peluquero updatePeluquero(Peluquero peluquero) {
		LOGGER.info("Actualizando Peluquero " + peluquero.toString());
		Peluquero updated = getPeluquero(peluquero.getId());
		updated.update(peluquero);
		repository.updatePeluquero(updated);
		return updated;
	}

	public void habilitarPeluquero(Integer idPeluquero) {
		Peluquero peluquero = getPeluquero(idPeluquero);
		LOGGER.info("Habilitando Peluquero " + peluquero.toString());
		peluquero.setHabilitado(!peluquero.isHabilitado());
		repository.updatePeluquero(peluquero);
		
	}
	
	public TotalesPeluqueroDTO getTotalesPeluquero(int peluqueroId) {
		LOGGER.info("Obteniendo los totals del peluquero con ID: " +  peluqueroId);
		Integer turnos = turnosRepository.getCantidadTurnosPeluquero(peluqueroId);
		BigDecimal pago = turnosRepository.getTotalPagosTurnosPeluquero(peluqueroId);
		return new TotalesPeluqueroDTO(turnos, pago);
	}

	
	public void deletePeluquero(Integer idPeluquero) {
		LOGGER.info("Eliminando el Peluquero con ID" + idPeluquero);
		repository.deletePeluquero(idPeluquero);
	}
	
	public List<Peluquero> getPeluqueros() {
		LOGGER.info("Obteniendo todos los peluqueros");
		return repository.getPeluqueros();
	}

	public List<Peluquero> getPeluquerosHabilitados() {
		List<Peluquero> peluqueros = getPeluqueros();
		return peluqueros.stream().filter(p -> p.isHabilitado()).collect(Collectors.toList());
	}

	public void setTurnosRepository(TurnosRepository turnosRepository) {
		this.turnosRepository = turnosRepository;
	}

	public void setRepository(PeluquerosRepository repository) {
		this.repository = repository;
	}
	

}

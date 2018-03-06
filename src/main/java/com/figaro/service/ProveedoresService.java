package com.figaro.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.figaro.model.Proveedor;
import com.figaro.repository.ProveedoresRepository;

public class ProveedoresService {
	
	final static Logger LOGGER = Logger.getLogger(ProveedoresService.class);
	
	private ProveedoresRepository repository;
	
	public Proveedor getProveedor(int ProveedorID) {
		LOGGER.debug("Obteniendo el Proveedor con ID: " + ProveedorID);
		return repository.getProveedor(ProveedorID);
	}

	public Proveedor saveProveedor(Proveedor proveedor) {
		LOGGER.info("Guardando Proveedor: " + proveedor.toString());
		int newID = repository.saveProveedor(proveedor);
		proveedor.setId(newID);
		return proveedor;
	}
	
	public Proveedor updateProveedor(Proveedor proveedor) {
		LOGGER.info("Actualizando Proveedor " + proveedor.toString());
		Proveedor updated = getProveedor(proveedor.getId());
		updated.update(proveedor);
		repository.updateProveedor(updated);
		return updated;
	}
	
	public List<Proveedor> getAllProveedores(){
		LOGGER.debug("Obteniendo todos los Proveedors");
		return repository.getAll();
	}
	

	public Proveedor deleteProveedor(int proveedorId) {
		Proveedor proveedor = getProveedor(proveedorId);
		LOGGER.info("Eliminando proveedor: "+proveedor.toString());
		repository.deleteProveedor(proveedor);
		return proveedor;
	}
	
	public ProveedoresRepository getRepository() {
		return repository;
	}
	
	public void setRepository(ProveedoresRepository repository) {
		this.repository = repository;
	}

	public List<Proveedor> buscar(String search) {
		return repository.buscar(search);
	}


}
package com.figaro.service;

import java.util.List;
import static com.figaro.util.Constants.*;

import org.apache.log4j.Logger;

import com.figaro.model.Cliente;
import com.figaro.repository.ClientesRepository;

public class ClientesService {
	
	final static Logger LOGGER = Logger.getLogger(ClientesService.class);
	
	private ClientesRepository repository;
	
	public Cliente getCliente(int clienteID) {
		LOGGER.debug("Obteniendo el cliente con ID: " + clienteID);
		return repository.getCliente(clienteID);
	}

	public Cliente saveCliente(Cliente cliente) {
		LOGGER.info("Guardando cliente: " + cliente.toString());
		int newID = repository.saveCliente(cliente);
		cliente.setId(newID);
		return cliente;
	}
	
	public Cliente updateCliente(Cliente cliente) {
		LOGGER.info("Actualizando Cliente " + cliente.toString());
		Cliente updated = getCliente(cliente.getId());
		updated.update(cliente);
		repository.updateCliente(updated);
		return updated;
	}
	
	public List<Cliente> getAllClientes(){
		LOGGER.info("Obteniendo todos los clientes");
		List<Cliente> clientes = repository.getAll();
		removeClienteDesconocido(clientes);  
		return clientes;
	}

	public List<Cliente> buscar(String search) {
		LOGGER.info("Buscando cliente: " + search);
		List<Cliente> clientes = repository.buscar(search);
		removeClienteDesconocido(clientes);
		return clientes;
	}

	private void removeClienteDesconocido(List<Cliente> clientes) {
		clientes.removeIf(c -> NOMBRE_CLIENTE_DESCONOCIDO.equals(c.getNombre()) && APELLIDO_CLIENTE_DESCONOCIDO.equals(c.getApellido()));
	}
	
	public Cliente getClienteDesconocido() {
		List<Cliente> resultadoBusqueda = buscar(NOMBRE_CLIENTE_DESCONOCIDO+" "+APELLIDO_CLIENTE_DESCONOCIDO);
		Cliente desconocido = new Cliente();
		if (resultadoBusqueda.isEmpty()) {
			desconocido.setNombre(NOMBRE_CLIENTE_DESCONOCIDO);
			desconocido.setApellido(APELLIDO_CLIENTE_DESCONOCIDO);
			saveCliente(desconocido);
		}else
			desconocido = resultadoBusqueda.get(0);
		return desconocido;
	}
	
	public ClientesRepository getRepository() {
		return repository;
	}
	
	public void setRepository(ClientesRepository repository) {
		this.repository = repository;
	}

	
	

}
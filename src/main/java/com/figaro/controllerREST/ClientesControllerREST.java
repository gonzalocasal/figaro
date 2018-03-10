package com.figaro.controllerREST;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.model.Cliente;
import com.figaro.service.ClientesService;

@RestController
@RequestMapping(value = "/rest/")
public class ClientesControllerREST {
	
	@Autowired
	@Qualifier("ClientesServiceTransactional")
	private ClientesService service;
	
	@GetMapping("clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        return new ResponseEntity<>(service.getAllClientes(), HttpStatus.OK);
    }
	
	@GetMapping("clientes/{clienteID}")
    public ResponseEntity<Cliente> getCliente( @PathVariable int clienteID) {
		return new ResponseEntity<>(service.getCliente(clienteID), HttpStatus.OK);
    }
	
	@PostMapping("clientes/alta")
    public ResponseEntity<Cliente> newCliente(@RequestBody Cliente cliente) {
		return new ResponseEntity<>(service.saveCliente(cliente), HttpStatus.CREATED);
	}
	
	@PutMapping("clientes/actualizar/{clienteID}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) {
		Cliente updated = service.updateCliente(cliente);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@GetMapping("clientes/buscar")
    public ResponseEntity<List<Cliente>> searchClientes(@RequestParam String search) {
		return new ResponseEntity<>(service.buscar(search), HttpStatus.OK);
    }
	
	public ClientesService getService() {
		return service;
	}
	
	public void setService(ClientesService service) {
		this.service = service;
	}
	
}

package com.figaro.controllerREST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.model.Proveedor;
import com.figaro.service.ProveedoresService;

@RestController
@RequestMapping(value = "/rest/")
public class ProveedoresControllerREST {
	
	@Autowired
	@Qualifier("ProveedoresServiceTransactional")
	private ProveedoresService service;
	
	@GetMapping("proveedores")
    public ResponseEntity<List<Proveedor>> getAllProveedores() {
        return new ResponseEntity<List<Proveedor>>(service.getAllProveedores(), HttpStatus.OK);
    }
	
	@GetMapping("proveedores/{proveedorId}")
    public ResponseEntity<Proveedor> geProveedor( @PathVariable int proveedorId) {
		return new ResponseEntity<Proveedor>(service.getProveedor(proveedorId), HttpStatus.OK);
    }
	
	@PostMapping("proveedores/alta")
    public ResponseEntity<Proveedor> newProveedor(@RequestBody Proveedor proveedor) {
		return new ResponseEntity<Proveedor>(service.saveProveedor(proveedor), HttpStatus.CREATED);
	}
	
	@DeleteMapping("proveedores/eliminar/{proveedorId}")
    public ResponseEntity<Proveedor> deleteProveedor( @PathVariable int proveedorId) {
		return new ResponseEntity<Proveedor>(service.deleteProveedor(proveedorId), HttpStatus.OK);
    }
	
	@PutMapping("proveedores/actualizar/{proveedorId}")
    public ResponseEntity<Proveedor> updateProveedor(@RequestBody Proveedor proveedor) {
		Proveedor updated = service.updateProveedor(proveedor);
		return new ResponseEntity<Proveedor>(updated, HttpStatus.OK);
	}

	@GetMapping("proveedores/buscar")
    public ResponseEntity<List<Proveedor>> searchProveedores(@RequestParam String search) {
		return new ResponseEntity<List<Proveedor>>(service.buscar(search), HttpStatus.OK);
    }
	
	public ProveedoresService getService() {
		return service;
	}
	
	public void setService(ProveedoresService service) {
		this.service = service;
	}
	
}

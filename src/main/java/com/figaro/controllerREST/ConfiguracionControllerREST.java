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

import com.figaro.model.Categoria;
import com.figaro.model.Ciudad;
import com.figaro.model.Servicio;
import com.figaro.service.ConfiguracionService;

@RestController
@RequestMapping(value = "/rest/configuracion")
public class ConfiguracionControllerREST {

	@Autowired
	@Qualifier("ConfiguracionServiceTransactional")
	private ConfiguracionService service;
	
	@GetMapping("/ciudades")
    public ResponseEntity<List<Ciudad>> getCiudades() {
		return new ResponseEntity<List<Ciudad>>(service.getCiudades(), HttpStatus.OK);
	}
	
	@PostMapping("/ciudades/alta")
    public ResponseEntity<Ciudad> newCiudad(@RequestBody Ciudad ciudad) {
		Integer newID = service.saveCiudad(ciudad);
		ciudad.setId(newID);
		return new ResponseEntity<Ciudad>(ciudad, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/ciudades/baja/{idCiudad}")
    public ResponseEntity<Ciudad> deleteCiudad(@PathVariable Integer idCiudad) {
		service.deleteCiudad(idCiudad);
		return new ResponseEntity<Ciudad>(HttpStatus.OK);
	}
	
	@GetMapping("/servicios")
    public ResponseEntity<List<Servicio>> getTrabajos() {
		return new ResponseEntity<List<Servicio>>(service.getServicio(), HttpStatus.OK);
	}
	
	@PostMapping("/servicios/alta")
    public ResponseEntity<Servicio> addTrabajo(@RequestBody Servicio trabajo) {
		Integer newID = service.saveServicio(trabajo);
		trabajo.setId(newID);
		return new ResponseEntity<Servicio>(trabajo, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/servicios/baja/{idServicio}")
    public ResponseEntity<Servicio> deleteServicio(@PathVariable Integer idServicio) {
		service.deleteServicio(idServicio);
		return new ResponseEntity<Servicio>(HttpStatus.OK);
	}
	
	@PutMapping("/servicios/actualizar/{idServicio}")
    public ResponseEntity<Servicio> updateServicio(@RequestBody Servicio servicio) {
		Servicio updated = service.updateServicio(servicio);
		return new ResponseEntity<Servicio>(updated ,HttpStatus.OK);
	}
	
	@GetMapping("/servicios/{idServicio}")
    public ResponseEntity<Servicio> getServicio(@PathVariable Integer idServicio) {
		Servicio trabajo = service.getServicio(idServicio);
		return new ResponseEntity<Servicio>(trabajo ,HttpStatus.OK);
	}
	
	@GetMapping("/servicios/buscar")
    public ResponseEntity<List<Servicio>> searchServicio(@RequestParam String search) {
        return new ResponseEntity<List<Servicio>>(service.buscarTrabajos(search), HttpStatus.OK);
    }
	
	
	@GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> getCategorias() {
		return new ResponseEntity<List<Categoria>>(service.getCategorias(), HttpStatus.OK);
	}
	
	@PostMapping("/categorias/alta")
    public ResponseEntity<Categoria> addCategoria(@RequestBody Categoria categoria) {
		Integer newID = service.save(categoria);
		categoria.setId(newID);
		return new ResponseEntity<Categoria>(categoria, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/categorias/baja/{idCategoria}")
    public ResponseEntity<Categoria> deleteCategoria(@PathVariable Integer idCategoria) {
		service.deleteCategoria(idCategoria);
		return new ResponseEntity<Categoria>(HttpStatus.OK);
	}
	
}

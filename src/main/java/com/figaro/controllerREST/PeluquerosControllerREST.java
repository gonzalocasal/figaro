package com.figaro.controllerREST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.dto.TotalesPeluqueroDTO;
import com.figaro.model.Peluquero;
import com.figaro.service.PeluquerosService;

@RestController
@RequestMapping(value = "/rest")
public class PeluquerosControllerREST {

	@Autowired
	@Qualifier("PeluquerosServiceTransactional")
	private PeluquerosService service;
	
	
	@GetMapping("/peluqueros")
    public ResponseEntity<List<Peluquero>> getPeluqueros() {
		return new ResponseEntity<>(service.getPeluqueros(), HttpStatus.OK);
	}
	
	@GetMapping("/peluqueros/habilitados")
    public ResponseEntity<List<Peluquero>> getPeluquerosHabilitados() {
		return new ResponseEntity<>(service.getPeluquerosHabilitados(), HttpStatus.OK);
	}
	
	@GetMapping("/peluqueros/{peluqueroId}")
    public ResponseEntity<Peluquero> getPeluquero(@PathVariable int peluqueroId) {
		return new ResponseEntity<>(service.getPeluquero(peluqueroId), HttpStatus.OK);
	}
	
	@PostMapping("/peluqueros/alta")
    public ResponseEntity<Peluquero> addPeluquero(@RequestBody Peluquero peluquero) {
		Integer newId = service.savePeluquero(peluquero);
		peluquero.setId(newId);
		return new ResponseEntity<>(peluquero, HttpStatus.CREATED);
	}
	
	@PutMapping("/peluqueros/actualizar/{peluqueroId}")
    public ResponseEntity<Peluquero> updateCliente(@RequestBody Peluquero peluquero) {
		Peluquero updated = service.updatePeluquero(peluquero);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@PatchMapping("/peluqueros/{idPeluquero}/habilitar")
    public ResponseEntity<Peluquero> habilitarPeluquero(@PathVariable Integer idPeluquero) {
		service.habilitarPeluquero(idPeluquero);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/peluqueros/{peluqueroId}/totales")
    public ResponseEntity <TotalesPeluqueroDTO> getCantidadTurnosPeluquero( @PathVariable int peluqueroId) {
		return new ResponseEntity<>(service.getTotalesPeluquero(peluqueroId), HttpStatus.OK);
    }
	
}

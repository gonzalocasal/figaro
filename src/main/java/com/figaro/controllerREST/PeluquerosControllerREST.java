package com.figaro.controllerREST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.model.Peluquero;
import com.figaro.service.PeluquerosService;

@RestController
@RequestMapping(value = "/rest")
public class PeluquerosControllerREST {

	@Autowired
	@Qualifier("PeluquerosServiceTransactional")
	private PeluquerosService service;
	
	
	@RequestMapping(value = "/peluqueros",method=RequestMethod.GET)
    public ResponseEntity<List<Peluquero>> getPeluqueros() {
		return new ResponseEntity<List<Peluquero>>(service.getPeluqueros(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/peluqueros/habilitados",method=RequestMethod.GET)
    public ResponseEntity<List<Peluquero>> getPeluquerosHabilitados() {
		return new ResponseEntity<List<Peluquero>>(service.getPeluquerosHabilitados(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/peluqueros/{peluqueroId}",method=RequestMethod.GET)
    public ResponseEntity<Peluquero> getPeluquero(@PathVariable int peluqueroId) {
		return new ResponseEntity<Peluquero>(service.getPeluquero(peluqueroId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/peluqueros/alta",method=RequestMethod.POST)
    public ResponseEntity<Peluquero> addPeluquero(@RequestBody Peluquero peluquero) {
		Integer newId = service.savePeluquero(peluquero);
		peluquero.setId(newId);
		return new ResponseEntity<Peluquero>(peluquero, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/peluqueros/actualizar/{peluqueroId}",method=RequestMethod.PUT)
    public ResponseEntity<Peluquero> updateCliente(@RequestBody Peluquero peluquero) {
		Peluquero updated = service.updatePeluquero(peluquero);
		return new ResponseEntity<Peluquero>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/peluqueros/baja/{idPeluquero}",method=RequestMethod.DELETE)
    public ResponseEntity<Peluquero> deletePeluquero(@PathVariable Integer idPeluquero) {
		service.deletePeluquero(idPeluquero);
		return new ResponseEntity<Peluquero>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/peluqueros/{idPeluquero}/habilitar",method=RequestMethod.PATCH)
    public ResponseEntity<Peluquero> habilitarPeluquero(@PathVariable Integer idPeluquero) {
		service.habilitarPeluquero(idPeluquero);
		return new ResponseEntity<Peluquero>(HttpStatus.OK);
	}
	
}

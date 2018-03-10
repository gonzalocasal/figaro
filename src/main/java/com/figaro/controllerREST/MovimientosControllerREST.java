package com.figaro.controllerREST;

import static com.figaro.util.Constants.DATE_FORMAT;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.figaro.model.Item;
import com.figaro.model.Movimiento;
import com.figaro.model.Trabajo;
import com.figaro.model.Turno;
import com.figaro.service.MovimientosService;

@RestController
@RequestMapping(value = "/rest/")
public class MovimientosControllerREST {
	

	@Autowired
	@Qualifier("MovimientosServiceTransactional")
	private MovimientosService service;

	@GetMapping("movimientos/{movimientoID}")
    public Movimiento getMovimiento( @PathVariable int movimientoID) {
        return service.getMovimiento(movimientoID);
    }
	
	@PostMapping("movimientos/alta")
    public ResponseEntity<Movimiento> newMovimiento(@RequestBody Movimiento movimiento) {
		return new ResponseEntity<Movimiento>(service.saveMovimiento(movimiento), HttpStatus.CREATED);
	}
	
	@PutMapping("movimientos/actualizar/{movimientoID}")
    public ResponseEntity<Movimiento> updateMovimiento(@RequestBody Movimiento movimiento) {
		Movimiento updated = service.updateMovimiento(movimiento);
		return new ResponseEntity<Movimiento>(updated, HttpStatus.OK);
	}
	
	@GetMapping("movimientos/buscar")
    public ResponseEntity<List<Movimiento>> getAllMovimiento(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to, @RequestParam String category ) {
        return new ResponseEntity<List<Movimiento>> (service.searchMovimientos(from,to,category),HttpStatus.CREATED);
    }
	
	@DeleteMapping("movimientos/eliminar/{movimientoID}")	
    public ResponseEntity<Movimiento> getAllMovimiento(@PathVariable int movimientoID) {
		return new ResponseEntity<>(service.deleteMovimiento(movimientoID), HttpStatus.OK);
	}
	
	@GetMapping("movimientos/listaDeItems")
    public ResponseEntity<List<Item>> getItemId(@RequestParam Integer id) throws ParseException {		
        return new ResponseEntity<List<Item>>(service.getItemId(id), HttpStatus.OK);
    }
	
	@GetMapping("movimientos/turno")
    public ResponseEntity<Turno> getTurnoId(@RequestParam Integer id) throws ParseException {		
        return new ResponseEntity<Turno>(service.getTurnoId(id), HttpStatus.OK);
    }	
	
	@GetMapping("movimientos/setDeTrabajos")
    public ResponseEntity<Set<Trabajo>> getListTurnoId(@RequestParam Integer id) throws ParseException {		
        return new ResponseEntity<Set<Trabajo>>(service.getListTurnoId(id), HttpStatus.OK);
    }
	
	public MovimientosService getService() {
		return service;
	}
	
	public void setService(MovimientosService service) {
		this.service = service;
	}
}
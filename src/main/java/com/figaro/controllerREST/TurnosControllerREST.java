package com.figaro.controllerREST;

import static com.figaro.util.Constants.DATE_FORMAT;

import java.util.Date;
import java.util.List;

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

import com.figaro.dto.TurnoDTO;
import com.figaro.model.Movimiento;
import com.figaro.model.Turno;
import com.figaro.service.TurnosService;

@RestController
@RequestMapping(value = "/rest/")
public class TurnosControllerREST {
	
	
	@Autowired
	@Qualifier("TurnosServiceTransactional")
	private TurnosService service;
	
	@PostMapping("turnos/alta")
    public ResponseEntity<Turno> newTurno(@RequestBody Turno turno) {
		return new ResponseEntity<>(service.saveTurno(turno), HttpStatus.CREATED);
	}
	
	@PostMapping("turnos/ocupado")
    public ResponseEntity<Boolean> isEmpleadoOcupado(@RequestBody Turno turno) {
		return new ResponseEntity<>(service.isEmpleadoOcupado(turno), HttpStatus.OK);
	}
	
	@GetMapping("turnos/{turnoId}")
    public ResponseEntity<Turno> getTurno( @PathVariable int turnoId) {
		return new ResponseEntity<>(service.getTurno(turnoId), HttpStatus.OK);
    }
	
	@GetMapping("turnos/cliente/{clienteId}")
    public ResponseEntity<List<TurnoDTO>> getTurnosCliente( @PathVariable int clienteId) {
		return new ResponseEntity<>(service.getTurnosCliente(clienteId), HttpStatus.OK);
    }
	
	@GetMapping("turnos/empleados/{empleadoId}")
    public ResponseEntity<List<TurnoDTO>> getTurnosEmpleado( @PathVariable int empleadoId, @RequestParam int index) {
		return new ResponseEntity<>(service.getTurnosEmpleado(empleadoId, index), HttpStatus.OK);
    }
	
	@GetMapping("turnos/empleados/{empleadoId}/sinpagar")
    public ResponseEntity<List<TurnoDTO>> getTurnosEmpleadoSinPagar( @PathVariable int empleadoId) {
		return new ResponseEntity<>(service.getTurnosEmpleadoSinPagar(empleadoId), HttpStatus.OK);
    }
	
	
	@PutMapping("turnos/actualizar/{turnoId}")
    public ResponseEntity<Turno> updateTurno(@RequestBody Turno turno) {
		Turno updated = service.updateTurno(turno);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@PutMapping("turnos/{turnoId}/cobrado")
    public ResponseEntity<Turno> setCobrado( @PathVariable int turnoId, @RequestBody Movimiento movimiento) {
		return new ResponseEntity<>(service.setCobrado(turnoId,movimiento), HttpStatus.OK);
	}
	
	@PutMapping("turnos/{turnoId}/cobrado/cancelar")
    public ResponseEntity<Turno> cancelCobro( @PathVariable int turnoId) {
		return new ResponseEntity<>(service.cancelCobro(turnoId), HttpStatus.OK);
	}
	
	@PutMapping("turnos/{turnoId}/pagar")
    public ResponseEntity<Turno> pago( @PathVariable int turnoId) {
		return new ResponseEntity<>(service.pagar(turnoId), HttpStatus.OK);
	}
	
	@GetMapping("turnos")
    public ResponseEntity<List<TurnoDTO>> getTurnosDelDia(@RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date fecha) {
		return new ResponseEntity<>(service.getTurnosDelDia(fecha), HttpStatus.CREATED);
	}
	
	@DeleteMapping("turnos/eliminar/{turnoId}")
    public ResponseEntity<Turno> deleteTurno(@PathVariable int turnoId) {
		return new ResponseEntity<>(service.deleteTurno(turnoId), HttpStatus.OK);
	}
	
	public TurnosService getService() {
		return service;
	}

	public void setService(TurnosService service) {
		this.service = service;
	}
	
}

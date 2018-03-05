package com.figaro.controllerREST;

import static com.figaro.util.Constants.DATE_FORMAT;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	
	@RequestMapping(value = "turnos/alta",method=RequestMethod.POST)
    public ResponseEntity<Turno> newTurno(@RequestBody Turno turno) {
		return new ResponseEntity<Turno>(service.saveTurno(turno), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "turnos/{turnoId}",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity<Turno> getTurno( @PathVariable int turnoId) {
		return new ResponseEntity<Turno>(service.getTurno(turnoId), HttpStatus.OK);
        
    }
	
	@RequestMapping(value = "turnos/cliente/{clienteId}",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity<List<TurnoDTO>> getTurnosCliente( @PathVariable int clienteId) {
		return new ResponseEntity<List<TurnoDTO>>(service.getTurnosCliente(clienteId), HttpStatus.OK);
    }
	
	@RequestMapping(value = "turnos/peluquero/{peluqueroId}",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity<List<TurnoDTO>> getTurnosPeluquero( @PathVariable int peluqueroId, @RequestParam int index) {
		return new ResponseEntity<List<TurnoDTO>>(service.getTurnosPeluquero(peluqueroId, index), HttpStatus.OK);
    }
	
	@RequestMapping(value = "turnos/peluquero/{peluqueroId}/sinpagar",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity<List<TurnoDTO>> getTurnosPeluqueroSinPagar( @PathVariable int peluqueroId) {
		return new ResponseEntity<List<TurnoDTO>>(service.getTurnosPeluqueroSinPagar(peluqueroId), HttpStatus.OK);
    }
	
	@RequestMapping(value = "turnos/peluquero/{peluqueroId}/cantidad",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity <Integer> getCantidadTurnosPeluquero( @PathVariable int peluqueroId) {
		return new ResponseEntity<Integer>(service.getCantidadTurnosPeluquero(peluqueroId), HttpStatus.OK);
    }
	
	@RequestMapping(value = "turnos/actualizar/{turnoId}",method=RequestMethod.PUT)
    public ResponseEntity<Turno> updateTurno(@RequestBody Turno turno) {
		Turno updated = service.updateTurno(turno);
		return new ResponseEntity<Turno>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "turnos/{turnoId}/cobrado",method=RequestMethod.PUT)
    public ResponseEntity<Turno> setCobrado( @PathVariable int turnoId, @RequestBody Movimiento movimiento) {
		return new ResponseEntity<Turno>(service.setCobrado(turnoId,movimiento), HttpStatus.OK);
	}
	
	@RequestMapping(value = "turnos/{turnoId}/cobrado/cancelar",method=RequestMethod.PUT)
    public ResponseEntity<Turno> cancelCobro( @PathVariable int turnoId) {
		return new ResponseEntity<Turno>(service.cancelCobro(turnoId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "turnos/{turnoId}/pagar",method=RequestMethod.PUT)
    public ResponseEntity<Turno> pago( @PathVariable int turnoId) {
		return new ResponseEntity<Turno>(service.pagar(turnoId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "turnos",method=RequestMethod.GET)
    public ResponseEntity<List<TurnoDTO>> getTurnosDelDia(@RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date fecha) {
		return new ResponseEntity<List<TurnoDTO>>(service.getTurnosDelDia(fecha), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value = "turnos/eliminar/{turnoId}",method=RequestMethod.DELETE)
    public ResponseEntity<Turno> getTurnosDelDia(@PathVariable int turnoId) {
		return new ResponseEntity<>(service.deleteTurno(turnoId), HttpStatus.OK);
	}
	
	public TurnosService getService() {
		return service;
	}

	public void setService(TurnosService service) {
		this.service = service;
	}
	
}

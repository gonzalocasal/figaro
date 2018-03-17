package com.figaro.controllerREST;

import static com.figaro.util.Constants.DATE_FORMAT;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.dto.TotalesEmpleadoDTO;
import com.figaro.model.Empleado;
import com.figaro.service.EmpleadosService;

@RestController
@RequestMapping(value = "/rest")
public class EmpleadosControllerREST {

	@Autowired
	@Qualifier("EmpleadosServiceTransactional")
	private EmpleadosService service;
	
	
	@GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> getEmpleados() {
		return new ResponseEntity<>(service.getEmpleados(), HttpStatus.OK);
	}
	
	@GetMapping("/empleados/habilitados")
    public ResponseEntity<List<Empleado>> getEmpleadosHabilitados() {
		return new ResponseEntity<>(service.getEmpleadosHabilitados(), HttpStatus.OK);
	}
	
	@GetMapping("/empleados/disponibles")
    public ResponseEntity<List<Empleado>> getEmpleadosDisponibles(@RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date fecha) {
		return new ResponseEntity<>(service.getEmpleadosDisponibles(fecha), HttpStatus.OK);
	}
	
	@GetMapping("/empleados/{empleadoId}")
    public ResponseEntity<Empleado> getEmpleado(@PathVariable int empleadoId) {
		return new ResponseEntity<>(service.getEmpleado(empleadoId), HttpStatus.OK);
	}
	
	@PostMapping("/empleados/alta")
    public ResponseEntity<Empleado> addEmpleado(@RequestBody Empleado empleado) {
		Integer newId = service.saveEmpleado(empleado);
		empleado.setId(newId);
		return new ResponseEntity<>(empleado, HttpStatus.CREATED);
	}
	
	@PutMapping("/empleados/actualizar/{empleadoId}")
    public ResponseEntity<Empleado> updateCliente(@RequestBody Empleado empleado) {
		Empleado updated = service.updateEmpleado(empleado);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@PatchMapping("/empleados/{idEmpleado}/habilitar")
    public ResponseEntity<Empleado> habilitarEmpleado(@PathVariable Integer idEmpleado) {
		service.habilitarEmpleado(idEmpleado);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/empleados/{empleadoId}/totales")
    public ResponseEntity <TotalesEmpleadoDTO> getCantidadTurnosEmpleado( @PathVariable int empleadoId) {
		return new ResponseEntity<>(service.getTotalesEmpleado(empleadoId), HttpStatus.OK);
    }
	
}

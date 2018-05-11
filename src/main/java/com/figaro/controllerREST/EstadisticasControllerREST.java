package com.figaro.controllerREST;

import static com.figaro.util.Constants.DATE_FORMAT;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.service.EstadisticasService;

@RestController
@RequestMapping(value = "/rest/")
public class EstadisticasControllerREST {
	
	@Autowired
	@Qualifier("EstadisticasServiceTransactional")
	private EstadisticasService service;
	
	@GetMapping("estadisticas/clientes/ciudad")
    public ResponseEntity<Map<String, Integer>> buscarClienteCiudad() {
        return new ResponseEntity<>(service.buscarClienteCiudad(), HttpStatus.OK);
    }
	
	@GetMapping("estadisticas/clientes/sexo")
    public ResponseEntity<Map<String, Integer>> buscarClienteSexo() {
        return new ResponseEntity<>(service.buscarClienteSexo(), HttpStatus.OK);
    }
	
	@GetMapping("estadisticas/productos/masvendido")
    public ResponseEntity<Map<String, Integer>> buscarProductoMasVendido(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {		
        return new ResponseEntity<>(service.buscarProductoMasVendido(from,to), HttpStatus.OK);
    }
	
	@GetMapping("estadisticas/caja/totales")
    public ResponseEntity<Map<String, BigDecimal>> buscarTotalesDeCaja(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to)  {		
        return new ResponseEntity<>(service.buscarTotalesDeCaja(from,to), HttpStatus.OK);
    }
		
	@GetMapping("estadisticas/turnos/empleado/cantidad")
    public ResponseEntity<Map<String, Integer>> buscarTurnosPorEmpleadoCant(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {		
        return new ResponseEntity<>(service.buscarTurnosPorEmpleadoCant(from,to), HttpStatus.OK);
    }
	
	@GetMapping("estadisticas/turnos/empleado/ingreso")
    public ResponseEntity<Map<String, BigDecimal>> buscarTurnosPorEmpleadoIngreso(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {		
        return new ResponseEntity<>(service.buscarTurnosPorEmpleadoIngreso(from,to), HttpStatus.OK);
    }
		
	@GetMapping("estadisticas/turnos/massolicitados")
    public ResponseEntity<TreeMap<String, Integer>> buscarTurnoMasSolicitado(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {		
        return new ResponseEntity<>(service.buscarTurnoMasSolicitado(from,to), HttpStatus.OK);
    }
	
	@GetMapping("estadisticas/turnos/turnomes")
    public ResponseEntity<TreeMap<String, Integer>> buscarTurnoMes(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {		
        return new ResponseEntity<>(service.buscarTurnoMes(from,to), HttpStatus.OK);
    }

	@GetMapping("estadisticas/turnos/turnosemana")
    public ResponseEntity<LinkedHashMap<String,Integer>> buscarTurnoSemana(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {		
        return new ResponseEntity<>(service.buscarTurnoSemana(from,to), HttpStatus.OK);
    }
	
	public EstadisticasService getService() {
		return service;
	}
	
	public void setService(EstadisticasService service) {
		this.service = service;
	}
}
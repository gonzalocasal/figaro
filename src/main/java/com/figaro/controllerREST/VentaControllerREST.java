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

import com.figaro.dto.VentaDTO;
import com.figaro.model.Venta;
import com.figaro.service.VentaService;



@RestController
@RequestMapping(value = "/rest/venta")
public class VentaControllerREST {
	
	@Autowired
	@Qualifier("VentaServiceTransactional")
	private VentaService service;
	
	@RequestMapping(value = "/todos",method=RequestMethod.GET,produces="application/json")
    public List<Venta> getAllVenta() {
        return service.getAllVenta();
    }
	
	@RequestMapping(value = "/{ventaId}",method=RequestMethod.GET,produces="application/json")
    public Venta getVenta(@PathVariable int ventaId) {
        return service.getVenta(ventaId);
    }
	
	@RequestMapping(value = "/alta",method=RequestMethod.POST)
    public ResponseEntity<Venta> newVenta(@RequestBody VentaDTO dto) {
		return new ResponseEntity<Venta>(service.saveVenta(dto), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/eliminar/{ventaId}",method=RequestMethod.DELETE)
    public ResponseEntity<Venta> deleteVenta(@PathVariable int ventaId) {
		return new ResponseEntity<Venta>(service.deleteVenta(ventaId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/historial-venta/buscar",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity<List<Venta>> getAllVenta(@RequestParam  @DateTimeFormat(pattern=DATE_FORMAT) Date from, @RequestParam @DateTimeFormat(pattern=DATE_FORMAT) Date to) {
        return new ResponseEntity<List<Venta>> (service.searchVentas(from,to),HttpStatus.CREATED);
    }
	
	public VentaService getVenta() {
		return service;
	}
	
	public void setService(VentaService service) {
		this.service = service;
	}
	
}
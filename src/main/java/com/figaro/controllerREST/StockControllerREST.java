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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.figaro.model.Producto;
import com.figaro.service.ProductosService;

@RestController
@RequestMapping(value = "/rest/stock")
public class StockControllerREST {
	
	@Autowired
	@Qualifier("ProductosServiceTransactional")
	private ProductosService service;
	
	@RequestMapping(value = "/todos",method=RequestMethod.GET,produces="application/json")
    public List<Producto> getAllProductos() {
        return service.getAllProductos();
    }
	
	@RequestMapping(value = "/faltante",method=RequestMethod.GET,produces="application/json")
    public List<Producto> getProductosFaltantes() {
        return service.buscarFaltante();	
	}
	
	@RequestMapping(value = "/{productoId}",method=RequestMethod.GET,produces="application/json")
    public Producto getProducto(@PathVariable int productoId) {
        return service.getProducto(productoId);
    }
	
	@RequestMapping(value = "/alta",method=RequestMethod.POST)
    public ResponseEntity<Producto> newProducto(@RequestBody Producto producto) {
		return new ResponseEntity<Producto>(service.saveProducto(producto), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/actualizar/{productoId}",method=RequestMethod.PUT)
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) {
		Producto updated = service.updateProducto(producto);
		return new ResponseEntity<Producto>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/editar/{productoId}",method=RequestMethod.PATCH)
    public ResponseEntity<Producto> editProducto(@PathVariable int productoId, @RequestParam int cantidad) {
		Producto updated = service.updateCantidad(productoId, cantidad);
		return new ResponseEntity<Producto>(updated, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/eliminar/{productoId}",method=RequestMethod.DELETE)
    public ResponseEntity<Producto> deleteProducto(@PathVariable int productoId) {
		return new ResponseEntity<Producto>(service.deleteProducto(productoId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/buscar",method=RequestMethod.GET,produces="application/json")
    public ResponseEntity<List<Producto>> getAllProductos(@RequestParam String search) {
        return new ResponseEntity<List<Producto>>(service.buscar(search), HttpStatus.OK);
    }
		
	public ProductosService getService() {
		return service;
	}
	
	public void setService(ProductosService service) {
		this.service = service;
	}
	

}
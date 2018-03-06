package com.figaro.controllerREST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.figaro.model.Producto;
import com.figaro.service.ProductosService;

@RestController
@RequestMapping("/rest/stock")
public class StockControllerREST {
	
	@Autowired
	@Qualifier("ProductosServiceTransactional")
	private ProductosService service;
	
	@GetMapping("/todos")
    public List<Producto> getAllProductos() {
        return service.getAllProductos();
    }
	
	@GetMapping("/faltante")
    public List<Producto> getProductosFaltantes() {
        return service.buscarFaltante();	
	}
	
	@GetMapping("/{productoId}")
    public Producto getProducto(@PathVariable int productoId) {
        return service.getProducto(productoId);
    }
	
	@PostMapping("/alta")
    public ResponseEntity<Producto> newProducto(@RequestBody Producto producto) {
		return new ResponseEntity<Producto>(service.saveProducto(producto), HttpStatus.CREATED);
	}
	
	@PutMapping("/actualizar/{productoId}")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) {
		Producto updated = service.updateProducto(producto);
		return new ResponseEntity<Producto>(updated, HttpStatus.OK);
	}
	
	@PatchMapping("/editar/{productoId}")
    public ResponseEntity<Producto> editProducto(@PathVariable int productoId, @RequestParam int cantidad) {
		Producto updated = service.updateCantidad(productoId, cantidad);
		return new ResponseEntity<Producto>(updated, HttpStatus.OK);
	}
	
	@DeleteMapping("/eliminar/{productoId}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable int productoId) {
		return new ResponseEntity<Producto>(service.deleteProducto(productoId), HttpStatus.OK);
	}
	
	@GetMapping("/buscar")
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
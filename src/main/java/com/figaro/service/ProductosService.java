package com.figaro.service;

import java.util.List;
import org.apache.log4j.Logger;
import com.figaro.exception.DatoDuplicadoException;
import com.figaro.model.Producto;
import com.figaro.repository.ProductosRepository;
import static com.figaro.util.Constants.MSG_DUPLICADO;

public class ProductosService {

	final static Logger LOGGER = Logger.getLogger(ProductosService.class);
	
	private ProductosRepository repository;

	
	public Producto getProducto(int productoID) {
		LOGGER.debug("Obteniendo el Producto con ID: " + productoID);
		return repository.getProducto(productoID);
	}
	
	public Producto saveProducto (Producto producto) {
		LOGGER.info("Guardando el Producto: " + producto.toString());
		validateProducto(producto);
		int id = repository.saveProducto(producto);
		producto.setId(id);
		return producto;
	}	
		
	public void validateProducto(Producto producto) {
		LOGGER.info("Validando el Producto: " + producto.toString());	
		Boolean existeProducto = repository.existeProducto(producto.getNombre(), producto.getDescripcion());
		if (existeProducto) {
			throw new DatoDuplicadoException(MSG_DUPLICADO);
		}
	}
	
	public Producto updateProducto(Producto producto) {
		Producto updated = getProducto(producto.getId());
		LOGGER.info("Actualizando el Producto: " + producto.toString());
		updated.update(producto);
		repository.updateProducto(updated);
		return updated;
	}
	
	public Producto updateCantidad(int productoId, int cantidad) {
		Producto updated = getProducto(productoId);
		updated.setCantidad(cantidad);
		LOGGER.info("Actualizando el Producto: " + updated.toString());
		repository.updateProducto(updated);
		return updated;
	}	
	
	public Producto deleteProducto(int productoId) {
		Producto producto = getProducto(productoId);
		LOGGER.info("Eliminando el Producto: " + producto.toString());
		repository.deleteProducto(producto);
		return producto;
	}
	
	public List<Producto> getAllProductos(){
		LOGGER.debug("Obteniendo todos los productos");
		return repository.getAll();
	}
	
	public List<Producto> buscarFaltante() {
		LOGGER.debug("Obteniendo todos los productos faltantes");
		return repository.buscarFaltante();
	}
	
	public ProductosRepository getRepository() {
		return repository;
	}
	public void setRepository(ProductosRepository repository) {
		this.repository = repository;
	}
	
	public List<Producto> buscar(String search) {
		return repository.buscar(search);
	}
	
	public Producto buscarDesdeVenta(String nombre, String descripcion) {
		return repository.buscarProductoDesdeVenta(nombre, descripcion);
	}
	
	
}

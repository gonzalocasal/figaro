package com.figaro.repository;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;

import com.figaro.model.Categoria;
import com.figaro.model.Ciudad;
import com.figaro.model.Movimiento;
import com.figaro.model.Servicio;

@SuppressWarnings("unchecked")
public class ConfiguracionRepository extends AbstractRepository {

	final static Logger LOGGER = Logger.getLogger(ConfiguracionRepository.class);
	
	//CIUDADES
	public Integer saveCiudad (Ciudad ciudad) {
		return (Integer) getCurrentSession().save(ciudad); 
	}

	public void deleteCiudad(Integer idCiudad) {
		Ciudad ciudad = getCurrentSession().load(Ciudad.class, idCiudad);
		LOGGER.info("Eliminando la ciudad: "+ ciudad.getNombre());
		getCurrentSession().delete(ciudad);
	}
	
	public List<Ciudad> getCiudades() {
		return getCurrentSession().createQuery("from Ciudad").list();
	}
	
	//SERVICIOS
	public Integer saveServicio(Servicio servicio) {
		return (Integer) getCurrentSession().save("Servicio",servicio);
	}

	public void deleteServicio(Integer idServicio) {
		Servicio servicio = (Servicio) getCurrentSession().get("Servicio", idServicio);
		LOGGER.info("Eliminando el Servicio: "+ servicio.getDescripcion());
		getCurrentSession().delete(servicio);
		
		Query<Movimiento> query = getCurrentSession().createNativeQuery("delete from TRABAJO_PELUQUERO WHERE ID_SERVICIO = ?1");
	    query.setParameter(1, idServicio);
	    query.executeUpdate();
	    

	}
	
	public List<Servicio> getServicios() {
		return getCurrentSession().createQuery("from Servicio").list();
	}
	
	public Servicio getServicio(Integer idServicio) {
		return (Servicio) getCurrentSession().get("Servicio", idServicio);
	}
	
	public List<Servicio> buscar(String search) {
		Query<Servicio> query = getCurrentSession().createQuery("FROM Servicio t WHERE t.descripcion LIKE CONCAT('%',:search,'%')");
		query.setParameter("search", search);
	    return query.getResultList();
	}

	//CATEGORIAS
	public List<Categoria> getCategorias() {
		return getCurrentSession().createQuery("from Categoria").list();
	}

	public Integer saveCategoria(Categoria categoria) {
		return (Integer) getCurrentSession().save(categoria);
	}

	public void deleteCategoria(Integer idCategoria) {
		Categoria categoria = getCurrentSession().load(Categoria.class, idCategoria);
		LOGGER.info("Eliminando la Categoria: "+ categoria.getNombre());
		getCurrentSession().delete(categoria);
	}


}

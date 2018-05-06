package com.figaro.repository;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.query.Query;

import com.figaro.model.Movimiento;
import com.figaro.model.Trabajo;
import com.figaro.model.Turno;
import com.figaro.model.Venta;
import com.figaro.service.MovimientosService;

@SuppressWarnings({ "unchecked" })
public class MovimientosRepository extends AbstractRepository{
	
	

	private static String QUERY_GET_MOVIMIENTOS = "FROM Movimiento m WHERE (m.fecha BETWEEN ?1 AND ?2)";
	private static String QUERY_CATEGORIA = " AND (?3)";
	
	final static Logger LOGGER = Logger.getLogger(MovimientosService.class);
	
	public int saveMovimiento (Movimiento movimiento) {
		return (int) getCurrentSession().save(movimiento); 
	}

	public void updateMovimiento(Movimiento movimiento) {
		getCurrentSession().update(movimiento);
	}
	
	public Movimiento getMovimiento(int id){
		return getCurrentSession().get(Movimiento.class, id);
	}
	
	public void deleteMovimiento(Movimiento movimiento) {
		getCurrentSession().delete(movimiento);
	}
	

	public List<Movimiento> searchBetween(Date from, Date to) {
		String querySql = QUERY_GET_MOVIMIENTOS;
		Query<Movimiento> query = getCurrentSession().createQuery(querySql);
	    query.setParameter(1, from);
	    query.setParameter(2, to);
	    return query.getResultList();
	}

	public List<Movimiento> searchBetweenWithCategory(Date from, Date to, String category) {
		
		String listaCategoria = "";
		String listaBusqueda = category;
		List<String> elephantList = Arrays.asList(listaBusqueda.split(","));
		for (String categoria : elephantList) {
			if (listaCategoria == "") {
				listaCategoria = "AND (m.categoria ='" +categoria +"'";
			} else {						
				listaCategoria = listaCategoria + " OR m.categoria ='" +categoria +"'";
			}			
		}		
		listaCategoria = listaCategoria + ")";	
		String querySql = QUERY_GET_MOVIMIENTOS + listaCategoria;
		Query<Movimiento> query = getCurrentSession().createQuery(querySql);
	    query.setParameter(1, from);
	    query.setParameter(2, to);	    
	    //query.setParameter(3, category);
	    return query.getResultList();
	}		
		
	public Venta getVentaId(Integer id) {		
		
		String querySql = "from Venta t WHERE (t.id = ?1)";	
		Query<Venta> query = getCurrentSession().createQuery(querySql);
	    query.setParameter(1, id);	  
	    return query.getSingleResult();
		
	}
	
	public Turno getTurnoId(Integer id) {		
		
		String querySql = "from Turno t WHERE (t.id = ?1)";	
		Query<Turno> query = getCurrentSession().createQuery(querySql);
	    query.setParameter(1, id);	  
	    return query.getSingleResult();
		
	}
	
	public Trabajo getTrabajoId(Integer id) {		
		
		String querySql = "from Trabajo t WHERE (t.id = ?1)";	
		Query<Trabajo> query = getCurrentSession().createQuery(querySql);
	    query.setParameter(1, id);	  
	    return query.getSingleResult();
		
	}

}	
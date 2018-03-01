package com.figaro.repository;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import com.figaro.model.Item;
import com.figaro.model.Venta;



public class VentaRepository extends AbstractRepository {
	
	private static String QUERY_GET_VENTAS = "FROM Venta v WHERE (v.fecha BETWEEN ?1 AND ?2)";
	
	public Integer saveVenta(Venta venta) {
		return (Integer) getCurrentSession().save(venta);
	}

	public Integer saveItem(Item item) {
		return (Integer) getCurrentSession().save(item);
	}
	
	public void updateVenta(Venta venta) {
		getCurrentSession().update(venta);
	}
	
	public Venta getVenta (int idVenta) {
		return (Venta) getCurrentSession().get(Venta.class, idVenta);
	}

	public void deleteVenta(Venta venta) {
		getCurrentSession().delete(venta);
	}	
	
	@SuppressWarnings("unchecked")
	public List<Venta> getAll() {
		return getCurrentSession().createQuery("from Venta").list();		
	}

	@SuppressWarnings("unchecked")
	public List<Venta> searchBetween(Date from, Date to) {
		String querySql = QUERY_GET_VENTAS;
		Query<Venta> query = getCurrentSession().createQuery(querySql);
	    query.setParameter(1, from);
	    query.setParameter(2, to);
	    return query.getResultList();
	}
	
}

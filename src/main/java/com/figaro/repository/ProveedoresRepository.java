package com.figaro.repository;

import java.util.List;

import org.hibernate.query.Query;

import com.figaro.model.Proveedor;

public class ProveedoresRepository extends AbstractRepository{

	public Integer saveProveedor (Proveedor proveedor) {
		return (Integer) getCurrentSession().save(proveedor); 
	}

	public void updateProveedor(Proveedor proveedor) {
		getCurrentSession().update(proveedor);
	}
	
	public Proveedor getProveedor(int id){
		return (Proveedor) getCurrentSession().get(Proveedor.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Proveedor> getAll() {
		return getCurrentSession().createQuery("from Proveedor").list();		
	}

	@SuppressWarnings("unchecked")
	public List<Proveedor> buscar(String search) {
	    Query<Proveedor> query = getCurrentSession().createQuery("FROM Proveedor p WHERE p.nombre LIKE CONCAT('%',:search,'%')");
		query.setParameter("search", search);
	    return query.getResultList();
	}

	public void deleteProveedor(Proveedor proveedor) {
		getCurrentSession().delete(proveedor);
	}
	
}

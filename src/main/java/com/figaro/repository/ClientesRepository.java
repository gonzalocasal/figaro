package com.figaro.repository;

import java.util.List;

import org.hibernate.query.Query;

import com.figaro.model.Cliente;

public class ClientesRepository extends AbstractRepository{

	public Integer saveCliente (Cliente cliente) {
		return (Integer) getCurrentSession().save(cliente); 
	}

	public void updateCliente(Cliente cliente) {
		getCurrentSession().update(cliente);
	}
	
	public Cliente getCliente(int id){
		return getCurrentSession().get(Cliente.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> getAll() {
		return getCurrentSession().createQuery("from Cliente").list();		
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> buscar(String search) {
	    Query<Cliente> query = getCurrentSession().createQuery("FROM Cliente c WHERE CONCAT(c.nombre, ' ', c.apellido) LIKE CONCAT('%',:search,'%')");
		query.setParameter("search", search);
	    return query.getResultList();
	}
	
}

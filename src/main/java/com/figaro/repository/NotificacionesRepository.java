package com.figaro.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.figaro.model.Notificacion;

@SuppressWarnings("unchecked")
public class NotificacionesRepository extends AbstractRepository{
	
	@Value("${page.size}")
	private Integer pageSize;

	
	public Integer save(Notificacion notificacion) {
		return (Integer) getCurrentSession().save(notificacion); 
	}

	public void update(Notificacion notificacion) {
		getCurrentSession().update(notificacion);
	}
	
	
	public List<Notificacion> getAll() {
		return getCurrentSession().createQuery("from Notificacion n ORDER BY n.fecha DESC").list();		
	}

	public Integer getCantidadTotal() {
		return getAll().size();		
	}
	
	public Integer getCantidadSinLeer() {
		return getCurrentSession().createQuery("from Notificacion n WHERE n.leida=false").list().size();
	}

	public List<Notificacion> getNotificaciones(int index) {
		
		return getCurrentSession().createQuery( "FROM Notificacion n ORDER BY n.fecha DESC")
					.setFirstResult(index*pageSize)
					.setMaxResults(pageSize)
					.list();
	}
	
}

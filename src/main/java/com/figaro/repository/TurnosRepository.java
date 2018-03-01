package com.figaro.repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.figaro.model.Trabajo;
import com.figaro.model.Turno;

@SuppressWarnings("unchecked")
public class TurnosRepository extends AbstractRepository{
	
	@Value("${page.size}")
	private Integer pageSize;

	public Turno getTurno(int turnoId) {
		return (Turno) getCurrentSession().get(Turno.class, turnoId);
	}

	public int saveTurno (Turno turno) {
		for(Trabajo trabajo : turno.getTrabajos()) {
			trabajo.setId(null);
			trabajo.getServicio().setId(null);
		}
		return (int) getCurrentSession().save(turno); 
	}

	public void updateTurno(Turno turno) {
		for(Trabajo trabajo : turno.getTrabajos()) {
			trabajo.setId(null);
			trabajo.getServicio().setId(null);
		}
		getCurrentSession().update(turno);
	}


	public void updateTurnoCobro(Turno turno) {
		getCurrentSession().update(turno);
	}
	
	public void deleteTurno(Turno turno) {
		getCurrentSession().delete(turno);
	}
	
	public List<Turno> getTurnosCliente(int clienteId) {
		return getCurrentSession().createQuery( "FROM Turno AS t WHERE t.cliente.id = :clienteId ORDER BY t.desde DESC")
				.setParameter("clienteId", clienteId)
				.list();
	}
	
	
	public List<Turno> getTurnosPeluquero(int peluqueroId, int index ) {
		return getCurrentSession().createQuery( "FROM Turno AS t WHERE t.peluquero.id = :peluqueroId ORDER BY t.desde DESC")
				.setParameter("peluqueroId", peluqueroId)
				.setFirstResult(index*pageSize)
				.setMaxResults(pageSize)
				.list();
	}
	
	public List<Turno> getTurnosPeluqueroSinPagar(int peluqueroId) {
		return getCurrentSession().createQuery( "FROM Turno AS t WHERE t.peluquero.id = :peluqueroId AND t.pagado = false ORDER BY t.desde DESC")
				.setParameter("peluqueroId", peluqueroId)
				.list();
	}
	
	public Integer getCantidadTurnosPeluquero(int peluqueroId) {
		return getCurrentSession().createQuery( "FROM Turno AS t WHERE t.peluquero.id = :peluqueroId ORDER BY t.desde DESC")
				.setParameter("peluqueroId", peluqueroId).list().size();
	}
	
	public List<Turno> searchTurno (Date desdeParam) {
		LocalDate localDate = desdeParam.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Calendar calendar = Calendar.getInstance();
		calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth(), 0, 0, 0);
		Date desde = calendar.getTime();
		calendar.set(localDate.getYear(), localDate.getMonthValue()-1, localDate.getDayOfMonth(), 23, 59, 0);
		Date hasta = calendar.getTime();
		return getCurrentSession().createQuery( "FROM Turno AS t WHERE t.desde BETWEEN :desde AND :hasta ORDER by t.desde")
		.setParameter("desde", desde)
		.setParameter("hasta", hasta)
		.list();
	}

	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	

	
}

package com.figaro.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.figaro.model.Peluquero;

@SuppressWarnings("unchecked")
public class PeluquerosRepository extends AbstractRepository {

	final static Logger LOGGER = Logger.getLogger(PeluquerosRepository.class);

	public Integer savePeluquero(Peluquero peluquero) {
		return (Integer) getCurrentSession().save(peluquero);
	}

	public void updatePeluquero(Peluquero peluquero) {
		getCurrentSession().merge(peluquero);
		getCurrentSession().createQuery("delete from Trabajo tp WHERE tp.servicio is null").executeUpdate();
		getCurrentSession().createQuery("delete from Trabajo tp WHERE tp.peluquero is null").executeUpdate();
	}
	
	public void deletePeluquero(Integer idPeluquero) {
		Peluquero peluquero = getCurrentSession().load(Peluquero.class, idPeluquero);
		LOGGER.info("Eliminando el Peluquero: "+ peluquero.getNombre());
		getCurrentSession().delete(peluquero);
	}

	public List<Peluquero> getPeluqueros() {
		return getCurrentSession().createQuery("from Peluquero").list();
	}

	public Peluquero getPeluquero(int peluqueroId) {
		return (Peluquero) getCurrentSession().get(Peluquero.class, peluqueroId);
	}


	
}

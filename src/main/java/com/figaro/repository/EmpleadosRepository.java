package com.figaro.repository;

import java.util.List;

import org.apache.log4j.Logger;

import com.figaro.model.Empleado;

@SuppressWarnings("unchecked")
public class EmpleadosRepository extends AbstractRepository {

	final static Logger LOGGER = Logger.getLogger(EmpleadosRepository.class);

	public Integer saveEmpleado(Empleado empleado) {
		return (Integer) getCurrentSession().save(empleado);
	}

	public void updateEmpleado(Empleado empleado) {
		getCurrentSession().merge(empleado);
		getCurrentSession().createQuery("delete from Trabajo tp WHERE tp.servicio is null").executeUpdate();
		getCurrentSession().createQuery("delete from Trabajo tp WHERE tp.empleado is null").executeUpdate();
	}
	
	public void deleteEmpleado(Integer idEmpleado) {
		Empleado empleado = getCurrentSession().load(Empleado.class, idEmpleado);
		LOGGER.info("Eliminando el Empleado: "+ empleado.getNombre());
		getCurrentSession().delete(empleado);
	}

	public List<Empleado> getEmpleados() {
		return getCurrentSession().createQuery("from Empleado").list();
	}

	public Empleado getEmpleado(int idEmpleado) {
		return (Empleado) getCurrentSession().get(Empleado.class, idEmpleado);
	}


	
}

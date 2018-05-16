package com.figaro.serviceTest;

import org.junit.Before;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import com.figaro.model.Cliente;
import com.figaro.model.Empleado;
import com.figaro.model.Movimiento;
import com.figaro.model.Servicio;
import com.figaro.model.Trabajo;
import com.figaro.model.Turno;
import com.figaro.repository.TurnosRepository;
import com.figaro.service.ClientesService;
import com.figaro.service.TurnosService;


public class TurnosServiceTest {
    
	private TurnosRepository repository;
	private TurnosService service;
	private Turno turno;
	
	private Date turnoDate = new Date();
	

	@Before
	public void setUp() {
		this.service= new TurnosService();
		this.repository= mock(TurnosRepository.class);
		
		service.setRepository(repository);
		service.setClientesService(mock (ClientesService.class));

		Cliente cliente = new Cliente();
		cliente.setNombre("Julian");
		cliente.setApellido("Fernandez");
		
		Empleado empleado = new Empleado();
		empleado.setNombre("Luis");
		empleado.setApellido("Lopez");
		
		Servicio servicio = new Servicio();
		servicio.setDescripcion("Corte");
		servicio.setPrecio(new BigDecimal(200));
		
		Trabajo trabajo = new Trabajo();
		trabajo.setServicio(servicio);
		trabajo.setComision(20);
		trabajo.setEmpleado(empleado);
		
		empleado.getTrabajos().add(trabajo);
		
		this.turno = new Turno();
		
		turno.setCliente(cliente);
		turno.setEmpleado(empleado);
		turno.getTrabajos().add(trabajo);
		turno.setDesde(turnoDate);
		turno.setHasta(turnoDate);
		
		turno.calculateMontosTurno();
		
	}
	
	
	
	@Test
    public void pagoTest() {
		when(repository.getTurno(0)).thenReturn(turno);
	
		service.setPagado(0);
		
		assertTrue(turno.getPagado());
		assertEquals(new BigDecimal(40), turno.getMontoPago());
		assertEquals("Luis Lopez",turno.getPago().getDetalle());
    }
	
	
	@Test
    public void cobroTest() {
		when(repository.getTurno(0)).thenReturn(turno);
	
		Movimiento cobro = new Movimiento();
		cobro.setDescuento(new BigDecimal(10));
		
		service.setCobrado(0, cobro);

		assertTrue(turno.getCobrado());
		assertEquals(new BigDecimal(200), turno.getMontoCobro());
		assertEquals("Julian Fernandez",turno.getCobro().getDetalle());
		assertEquals(new BigDecimal(10),turno.getCobro().getDescuento());
		assertEquals(turnoDate,turno.getCliente().getUltimaVisita());
		
    }

}

package com.figaro.serviceTest;

import static com.figaro.util.Constants.*;
import org.junit.Before;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.figaro.exception.HorarioInvalidoException;
import com.figaro.exception.TurnoOcupadoEmpleadoException;
import com.figaro.exception.TurnoOcupadoException;
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
	private ClientesService clientesService;
	private Turno turno;
	private Date turnoDate = new Date();
	
	@Before
	public void setUp() {
		this.service= new TurnosService();
		this.repository= mock(TurnosRepository.class);
		this.clientesService = mock(ClientesService.class);
		
		service.setRepository(repository);
		service.setClientesService(clientesService);

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
		turno.setHasta(addHour());
		
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
	
	@Test
    public void cancelPagoTest() {
		when(repository.getTurno(0)).thenReturn(turno);
	
		service.setPagado(0);
		service.cancelPago(0);
		
		assertFalse(turno.getPagado());
		assertNull(turno.getPago());
    }
	
	@Test
    public void cancelCobroTest() {
		when(repository.getTurno(0)).thenReturn(turno);
	
		Movimiento cobro = new Movimiento();
		cobro.setDescuento(new BigDecimal(10));
		service.setCobrado(0, cobro);

		service.cancelCobro(0);
		
		assertFalse(turno.getCobrado());
		assertNull(turno.getCobro());
    }
	
	@Test
    public void clienteDesconocidoTest() {
		when(repository.getTurno(0)).thenReturn(turno);
		when(clientesService.getClienteDesconocido()).thenReturn(generateClienteDesconocido());
		
		turno.setCliente(null);
		service.saveTurno(turno);

		service.getTurno(0);
		assertEquals("Cliente",turno.getCliente().getNombre());
		assertEquals("Desconocido",turno.getCliente().getApellido());
    }
	
	
	@Test(expected = TurnoOcupadoException.class)
    public void clienteHorarioOcupadooTest() {
		when(repository.searchTurno(turnoDate)).thenReturn(generateTurnosList());
		service.saveTurno(turno);
    }
	
	@Test(expected = TurnoOcupadoEmpleadoException.class)
    public void empleadoHorarioOcupadoTest() {
		when(repository.searchTurno(turnoDate)).thenReturn(generateTurnosEmpleadoList());
		service.isEmpleadoOcupado(turno);
    }
	
	
	@Test(expected = HorarioInvalidoException.class)
    public void validateInvalidHorarioTurnoTest() {
		when(repository.getTurno(0)).thenReturn(turno);
		turno.setHasta(restHour());
		service.saveTurno(turno);
    }

	@Test(expected = HorarioInvalidoException.class)
    public void validateMismoHorarioTurnoTest() {
		when(repository.getTurno(0)).thenReturn(turno);
		turno.setHasta(turnoDate);
		service.saveTurno(turno);
    }
	
	
	private List<Turno> generateTurnosList() {
		List<Turno> turnos = new ArrayList<>();
		turnos.add(turno);
		turnos.add(turno);
		return turnos;
	}

	private List<Turno> generateTurnosEmpleadoList() {
		List<Turno> turnos = new ArrayList<>();
		turnos.add(turno);
		Turno otroTurnoEmpleado = new Turno();
		otroTurnoEmpleado.setEmpleado(turno.getEmpleado());
		otroTurnoEmpleado.setDesde(turnoDate);
		otroTurnoEmpleado.setHasta(addHour());
		otroTurnoEmpleado.setCliente(generateClienteDesconocido());
		
		turnos.add(otroTurnoEmpleado);
		return turnos;
	}
	
	private Cliente generateClienteDesconocido() {
		Cliente cliente = new Cliente();
		cliente.setNombre(CLIENTE_DESCONOCIDO_NOMBRE);
		cliente.setApellido(CLIENTE_DESCONOCIDO_APELLIDO);
		return cliente;
	}

	private Date restHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, -1);
		return cal.getTime();
	}
	
	private Date addHour() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, +1);
		return cal.getTime();
	}
	
}

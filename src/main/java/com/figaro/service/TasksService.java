package com.figaro.service;

import static com.figaro.util.Constants.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.figaro.dto.TurnoDTO;
import com.figaro.model.Empleado;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;

public class TasksService {
	
	final static Logger LOGGER = Logger.getLogger(TasksService.class);

	@Value("${jobs.enabled}")
	private Boolean jobsEnabled;
	private TurnosService turnosService;
	private EmpleadosService empleadosService;
	
	public void emailsJob() {
		if(!jobsEnabled) return;
		List<TurnoDTO> turnosDelDia = turnosService.getTurnosDelDia(new Date());
		empleadosJob(turnosDelDia);
		clientesJob(turnosDelDia);
	}
	
	public void  generateMailRequest(String email, String subject ,String message) {
	    Email from = new Email(EMAILS_FROM);
	    Email to = new Email(email);
	    Content content = new Content(EMAILS_TYPE, message);
	    Mail mail = new Mail(from, subject, to, content);
	    SendGrid sg = new SendGrid(System.getenv(EMAILS_SENDGRID_API_KEY));
	    Request request = new Request();
	    request.method = Method.POST;
	    request.endpoint = EMAILS_SENDGRID_MAIL_SEND;
	    sendMail(mail, sg, request);
	  }
	
	public void empleadosJob(List<TurnoDTO> turnosDelDia) {
		LOGGER.info("Enviando los emails a los empleados");
		List<Empleado> empleadosDisponibles = empleadosService.getEmpleadosDisponibles(new Date());
		for (Empleado e : empleadosDisponibles) {
			if (null != e.getEmail()) {
				List<TurnoDTO> turnosEmpleado = turnosDelDia.stream().filter(t -> t.getEmpleado().equals(e)).collect(Collectors.toList());
				String turnos ="";
				for(TurnoDTO t : turnosEmpleado) {
					String hora = formatHoraTurno(t);
					String cliente = t.getCliente().getNombre() +" "+t.getCliente().getApellido(); 
					String trabajos = t.getDescripcionTrabajos();
					turnos = turnos.concat("<p>"+hora+" "+trabajos+" a "+cliente+"</p>");
				}
				String email   = EMAILS_EMPLEADOS_TEMPLATE.replace("%name%", e.getNombre()).replace("%turnos%",turnos);
				String subject = EMAILS_EMPLEADOS_ASUNTO;
				generateMailRequest(e.getEmail(), subject, email);
			}
		}
	}

	
	public void clientesJob(List<TurnoDTO> turnosDelDia) {
		LOGGER.info("Enviando los emails a los clientes");
		for(TurnoDTO t : turnosDelDia) {
			if (null != t.getCliente().getEmail()) {
				String hora = formatHoraTurno(t);
				String empleado = t.getEmpleado().getNombre() + " "+ t.getEmpleado().getApellido(); 
				String trabajos = t.getDescripcionTrabajos();
				String turno = ("<p>"+hora+" "+trabajos+" con "+empleado+"</p>");
				String email   = EMAILS_CLIENTES_TEMPLATE.replace("%name%", t.getCliente().getNombre()).replace("%turnos%",turno);
				String subject = EMAILS_CLIENTES_ASUNTO;
				generateMailRequest(t.getCliente().getEmail(), subject, email);
			}
		}
	}
	
	private void sendMail(Mail mail, SendGrid sg, Request request){
		try {
			request.body = mail.build();
			sg.api(request);
		} catch (IOException ex) {
		    LOGGER.error("Error al intentar enviar email " + ex);  
		}
	}
	
	
	private String formatHoraTurno(TurnoDTO t) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT); 
		String desde = sdf.format(t.getDesde());
		String hasta = sdf.format(t.getHasta());
		return desde+" - "+hasta;
	}

	
	public void setTurnosService(TurnosService turnosService) {
		this.turnosService = turnosService;
	}
	public void setEmpleadosService(EmpleadosService empleadosService) {
		this.empleadosService = empleadosService;
	}
	public void setJobsEnabled(Boolean jobsEnabled) {
		this.jobsEnabled = jobsEnabled;
	}
}

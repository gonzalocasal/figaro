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

public class EmailsService {
	
	final static Logger LOGGER = Logger.getLogger(EmailsService.class);

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
	
	private void  generateMailRequest(String email, String subject ,String message) {
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
	
	private void empleadosJob(List<TurnoDTO> turnosDelDia) {
		LOGGER.info("Enviando los emails a los empleados");
		List<Empleado> empleadosDisponibles = empleadosService.getEmpleadosDisponibles(new Date());
		for (Empleado e : empleadosDisponibles) { 
			List<TurnoDTO> turnosEmpleado = turnosDelDia.stream().filter(t -> t.getEmpleado().equals(e)).collect(Collectors.toList());
			if (null != e.getEmail() && !turnosEmpleado.isEmpty()) {
				String turnos ="";
				for(TurnoDTO t : turnosEmpleado) {
					String hora = formatHoraTurno(t);
					String cliente = t.getCliente().getNombre() +" "+t.getCliente().getApellido(); 
					String trabajos = t.getDescripcionTrabajos();
					turnos = turnos.concat(String.format(EMAILS_TURNO_FORMAT_EMPLEADO, hora,trabajos,cliente));
				}
				String email   = EMAILS_EMPLEADOS_TEMPLATE.replace(EMAILS_TAG_NAME, e.getNombre()).replace(EMAILS_TAG_TURNOS,turnos);
				generateMailRequest(e.getEmail(), EMAILS_EMPLEADOS_ASUNTO, email);
			}
		}
	}
	
	private void clientesJob(List<TurnoDTO> turnosDelDia) {
		LOGGER.info("Enviando los emails a los clientes");
		for(TurnoDTO t : turnosDelDia) 
			if (null != t.getCliente().getEmail()) {
				String hora = formatHoraTurno(t);
				String empleado = t.getEmpleado().getNombre() + " "+ t.getEmpleado().getApellido(); 
				String trabajos = t.getDescripcionTrabajos();
				String turno = String.format(EMAILS_TURNO_FORMAT_CLIENTE, hora,trabajos,empleado);
				String email   = EMAILS_CLIENTES_TEMPLATE.replace(EMAILS_TAG_NAME, t.getCliente().getNombre()).replace(EMAILS_TAG_TURNOS,turno);
				generateMailRequest(t.getCliente().getEmail(), EMAILS_CLIENTES_ASUNTO, email);
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

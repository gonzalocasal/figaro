package com.figaro.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.figaro.dto.TurnoDTO;
import com.figaro.model.Empleado;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class TasksService {
	
	
	final static Logger LOGGER = Logger.getLogger(TasksService.class);
	
	private TurnosService turnosService;
	private EmpleadosService empleadosService;
	
	
	private String emailTemplate = "<html>\r\n" + 
			"    <head>\r\n" + 
			"        <title>Figaro Mail</title>\r\n" + 
			"    </head>\r\n" + 
			"    <body style=\"font-family:  Roboto-Regular,Helvetica,Arial,sans-serif; text-align: center; background-color: #f6f6f6; width:100%;\">\r\n" + 
			"        \r\n" + 
			"        <table style=\" display: inline-table; \r\n" + 
			"                       width: 600px; \r\n" + 
			"                       background-color: white;\r\n" + 
			"                       border-collapse: collapse; \r\n" + 
			"                       padding: :8px;\r\n" + 
			"                       border-bottom: 3px solid gainsboro;\">\r\n" + 
			"\r\n" + 
			"            <tr style=\"background-color: #292961; color: white;\" >\r\n" + 
			"                <td>Horario</td>\r\n" + 
			"                <td>Cliente</td>\r\n" + 
			"                <td>Trabajos</td>\r\n" + 
			"            </tr>\r\n" + 
			"            %s\r\n" + 
			"        </table>\r\n" + 
			"        <footer>\r\n" + 
			"            <img width=\"160\" height=\"80\" src=\"https://beta.figaro.com.ar/imgs/logo2.png\">\r\n" + 
			"        </footer>\r\n" + 
			"    </body>\r\n" + 
			"</html>";
	
	
	 public void  sendMail(String email, String subject ,String message) {
	    Email from = new Email("no-responder@figaro.com.ar");
	    
	    Email to = new Email(email);
	    Content content = new Content("text/html", message);
	    Mail mail = new Mail(from, subject, to, content);
	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    Request request = new Request();
	    try {
	      request.method = Method.POST;
	      request.endpoint = "mail/send";
	      request.body = mail.build();
	      Response response = sg.api(request);
	      LOGGER.info(response.toString());
	    } catch (IOException ex) {
	      LOGGER.error("Error ocurred trying to send email " + ex);
	      
	    }
	  }
	
	public void testJob() {
		LOGGER.info("CORRIENDO JOB");
		List<TurnoDTO> turnosDelDia = turnosService.getTurnosDelDia(new Date());
		List<Empleado> empleadosDisponibles = empleadosService.getEmpleadosDisponibles(new Date());
		for (Empleado e : empleadosDisponibles) {
			List<TurnoDTO> turnosEmpleado = turnosDelDia.stream().filter(t -> t.getEmpleado().equals(e)).collect(Collectors.toList());
			String turnos ="";
			for(TurnoDTO t : turnosEmpleado) {
				String turno = "<tr>%s</tr>";
				String hora = "<td>"+generateHoraTurno(t) +"</td>";
				String cliente = "<td>"+t.getCliente().getNombre() +" "+t.getCliente().getApellido()+"</td>"; 
				String trabajos = "<td>"+t.getDescripcionTrabajos()+"</td>";
				turno = String.format(turno, hora+cliente+trabajos);
				turnos = turnos.concat(turno);
			}
			String email   = emailTemplate.replace("%s", turnos);
			String subject = String.format("Buen día %s :) estos son tus turnos para hoy:", e.getNombre());
			sendMail(e.getEmail(), subject, email);
		}
	}

	private String generateHoraTurno(TurnoDTO t) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
		String desde = sdf.format(t.getDesde());
		String hasta = sdf.format(t.getHasta());
		return desde+" - "+hasta;
	}

	
	
	
	public TurnosService getTurnosService() {
		return turnosService;
	}

	public void setTurnosService(TurnosService turnosService) {
		this.turnosService = turnosService;
	}

	public EmpleadosService getEmpleadosService() {
		return empleadosService;
	}

	public void setEmpleadosService(EmpleadosService empleadosService) {
		this.empleadosService = empleadosService;
	}
}

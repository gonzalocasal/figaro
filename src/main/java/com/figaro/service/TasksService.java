package com.figaro.service;

import java.io.IOException;
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
	
	
	private String emailTemplate = " <html>\r\n" + 
			"    <head>\r\n" + 
			"        <title>Figaro Mail</title>\r\n" + 
			"    </head>\r\n" + 
			"    <body>\r\n" + 
			"        <h1 style=\"background-color: #292961;    box-shadow: rgba(0, 0, 0, 0.2) 0px 1px 3px 0px, rgba(0, 0, 0, 0.14) 0px 1px 1px 0px, rgba(0, 0, 0, 0.12) 0px 2px 1px -1px; \">\r\n" + 
			"        <img width=\"160\" height=\"80\" src=\"https://beta.figaro.com.ar/imgs/logo.png\">\r\n" + 
			"        </h1>\r\n" + 
			"        <table>\r\n" + 
			"            <tr style=\"background-color: #292961; color: white\">\r\n" + 
			"                <td>HORARIO</td>\r\n" + 
			"                <td>CLIENTE</td>\r\n" + 
			"                <td>TRABAJOS</td>\r\n" + 
			"            </tr>\r\n" + 
			"            %s\r\n" + 
			"        </table>\r\n" + 
			"    </body>\r\n" + 
			"\r\n" + 
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
				String hora = "<td>fruta</td>";
				String cliente = "<td>"+t.getCliente().getNombre() +" "+t.getCliente().getApellido()+"</td>"; 
				String trabajos = "<td>"+t.getDescripcionTrabajos()+"</td>";
				turno = String.format(turno, hora+cliente+trabajos);
				turnos = turnos.concat(turno);
			}
			String email   = String.format(emailTemplate, turnos);
			String subject = String.format("Buen día %s :) estos son tus turnos para hoy:", e.getNombre());
			sendMail(e.getEmail(), subject, email);
		}
		
		
		
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

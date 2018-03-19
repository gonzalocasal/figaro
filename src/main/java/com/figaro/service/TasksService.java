package com.figaro.service;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class TasksService {
	
	 public ResponseEntity<String>  sendMail() throws IOException {
	    Email from = new Email("no-responder@figaro.com.ar");
	    String subject = "FIGARO, empleados mail test";
	    Email to = new Email("gonzalocasal@gmail.com");
	    Content content = new Content("text/plain", "Hola Buen dia :) estos son tus turnos para hoy:");
	    Mail mail = new Mail(from, subject, to, content);
	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    Request request = new Request();
	    try {
	      request.method = Method.POST;
	      request.endpoint = "mail/send";
	      request.body = mail.build();
	      Response response = sg.api(request);
	    } catch (IOException ex) {
	      throw ex;
	    }
	    return new ResponseEntity<>("OK", HttpStatus.OK);
	  }
	
	
}

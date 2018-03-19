package com.figaro.service;


import com.sendgrid.*;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;


public class TasksService {

	@RequestMapping("/email")
	 public void sendMail() throws IOException {
	    Email from = new Email("test@example.com");
	    String subject = "Hello World from the SendGrid Java Library!";
	    Email to = new Email("gonzalocasal@gmail.com");
	    Content content = new Content("text/plain", "Hello, Email!");
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("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");
	    Request request = new Request();
	    try {
	      request.method = Method.POST;
	      request.endpoint = "mail/send";
	      request.body = mail.build();
	      Response response = sg.api(request);
	    } catch (IOException ex) {
	      throw ex;
	    }
	  }
	
	
}

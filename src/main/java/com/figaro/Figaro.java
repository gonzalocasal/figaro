package com.figaro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("applicationContext.xml")
@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class})
public class Figaro {
	
	public static void main(String[] args) {
		SpringApplication.run(Figaro.class, args);
	}

	
}

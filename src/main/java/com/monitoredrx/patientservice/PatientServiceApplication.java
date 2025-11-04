package com.monitoredrx.patientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PatientServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(PatientServiceApplication.class, args);
		
		
	}

}

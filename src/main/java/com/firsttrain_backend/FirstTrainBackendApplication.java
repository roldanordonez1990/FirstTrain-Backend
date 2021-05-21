package com.firsttrain_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //Necesario para habilitar el filtro jwt de seguridad
@SpringBootApplication
public class FirstTrainBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstTrainBackendApplication.class, args);
	}

}

package com.example.microservicio_crud_productos_reactivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ={"controller", "service"})
public class MicroservicioCrudProductosReactivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioCrudProductosReactivoApplication.class, args);
	}

}

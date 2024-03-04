package com.semillero.solicitudes;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SolicitudesApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(SolicitudesApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}

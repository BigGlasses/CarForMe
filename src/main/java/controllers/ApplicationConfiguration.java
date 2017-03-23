package controllers;

import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import application.VehicleParser;

@SpringBootApplication
public class ApplicationConfiguration {

	public static void main(String[] args) throws FileNotFoundException {
		VehicleParser.init();
		SpringApplication.run(ApplicationConfiguration.class, args);
	}

}
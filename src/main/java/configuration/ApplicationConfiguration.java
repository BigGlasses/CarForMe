package configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import application.VehicleParser;

/**
 * 
 * @author Brandon
 *	This is the main class for the SpringBoot Application
 */
@SpringBootApplication
public class ApplicationConfiguration {

	public static void main(String[] args) throws IOException {
		VehicleParser.init();
		SpringApplication.run(ApplicationConfiguration.class, args);
	}

}
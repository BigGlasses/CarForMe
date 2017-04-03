package configuration;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import application.VehicleParser;

/**
 * 
 * @author Brandon
 *	This is the main class for the SpringBoot Application
 */
@SpringBootApplication
public class ApplicationConfiguration extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationConfiguration.class);
    }
	public static void main(String[] args) throws IOException {
		VehicleParser.init();
		SpringApplication.run(ApplicationConfiguration.class, args);
	}

}
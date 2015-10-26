package com.library.essay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//Mark the class to be spring configuration equivalent to applicationContext.xml
@Configuration
// Scan @Service and @Component definitions
@ComponentScan(basePackages = { "com.library.essay" })
@EnableAutoConfiguration
public class SpringBootEssayLibraryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEssayLibraryApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application
				.sources(new Class[] { SpringBootEssayLibraryApplication.class, MyServletContextInitializer.class });
	}

}

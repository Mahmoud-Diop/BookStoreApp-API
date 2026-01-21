package com.library.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "com.library.Book.model")
@EnableJpaRepositories(basePackages = "com.library.Book.Persistence")
@SpringBootApplication(scanBasePackages = {
	"com.library",
	"com.example.first_project"
	
})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

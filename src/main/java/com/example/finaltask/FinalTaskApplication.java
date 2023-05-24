package com.example.finaltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class FinalTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalTaskApplication.class, args);
	}

}

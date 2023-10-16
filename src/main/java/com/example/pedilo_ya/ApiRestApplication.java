package com.example.pedilo_ya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.example.ApiREST.entities")
@ComponentScan(basePackages = {"com.example.pedilo_ya.controllers", "com.example.pedilo_ya.repositories", "com.example.pedilo_ya.configurationSpring"})
public class ApiRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

}

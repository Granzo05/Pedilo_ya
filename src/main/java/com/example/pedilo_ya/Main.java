package com.example.pedilo_ya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan("com.example.pedilo_ya.entities")
@ComponentScan(basePackages = {"com.example.pedilo_ya.controllers", "com.example.pedilo_ya.repositories", "com.example.pedilo_ya.configurationSpring"})
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://127.0.0.1:5500", "http://127.0.0.1:5500/login.html")
						.allowedMethods("GET", "POST", "PUT", "DELETE")
						.allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
						.allowCredentials(true);
			}
		};
	}
}

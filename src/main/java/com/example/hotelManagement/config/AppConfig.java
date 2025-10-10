package com.example.hotelManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ComponentScan
public class AppConfig {
	@Bean
	public OpenAPI employeesOpenApi() {
		return new OpenAPI().info(new Info().title("Employee API").version("1.0").description("Demo API"));
	}
}

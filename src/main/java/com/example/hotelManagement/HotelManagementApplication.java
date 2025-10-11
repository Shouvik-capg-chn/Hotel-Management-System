package com.example.hotelManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Hotel Management System", version = "1.0", description = "API documentation for Hotel Management System"))
@SpringBootApplication(scanBasePackages = "com.example.hotelManagement")
public class HotelManagementApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(HotelManagementApplication.class, args);
	}

}

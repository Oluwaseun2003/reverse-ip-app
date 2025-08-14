package com.example.reverse_ip_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class ReverseIpAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReverseIpAppApplication.class, args);
	}

}

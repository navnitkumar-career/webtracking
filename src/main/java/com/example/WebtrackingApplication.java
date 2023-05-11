package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebtrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebtrackingApplication.class, args);
	}
	static {

		System.setProperty("java.awt.headless", "false");
	}
}

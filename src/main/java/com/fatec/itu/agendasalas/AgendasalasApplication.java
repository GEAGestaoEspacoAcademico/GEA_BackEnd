package com.fatec.itu.agendasalas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AgendasalasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendasalasApplication.class, args);
	}

}

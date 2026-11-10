package com.example.day2_simulasiSpringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Day2SimulasiSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day2SimulasiSpringbootApplication.class, args);
	}

}

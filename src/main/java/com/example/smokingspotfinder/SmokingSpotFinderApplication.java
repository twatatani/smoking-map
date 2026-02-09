package com.example.smokingspotfinder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.smokingspotfinder.mapper")
public class SmokingSpotFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmokingSpotFinderApplication.class, args);
	}

}

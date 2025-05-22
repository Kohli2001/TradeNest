package com.epam.tradingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingappApplication.class, args);
	}

}

package com.akhona.sentinel.fraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SentinelFraudEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SentinelFraudEngineApplication.class, args);
	}

}

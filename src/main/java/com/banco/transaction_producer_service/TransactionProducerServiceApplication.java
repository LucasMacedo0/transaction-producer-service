package com.banco.transaction_producer_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class TransactionProducerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionProducerServiceApplication.class, args);
	}

}

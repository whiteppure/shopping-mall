package com.hbsi.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShoppingMallApplication {
	public static void main(String[] args) {
		SpringApplication.run(ShoppingMallApplication.class, args);
	}
}

package com.example.craft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.example.craft.repositories", "com.example.craft.entity"})
@EnableJpaRepositories(basePackages = {"com.example.craft.repositories", "com.example.craft.entity"})
@SpringBootApplication
@EnableCaching
public class CraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(CraftApplication.class, args);
	}

}

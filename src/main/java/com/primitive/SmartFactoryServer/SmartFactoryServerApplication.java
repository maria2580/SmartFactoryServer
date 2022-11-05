package com.primitive.SmartFactoryServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SmartFactoryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartFactoryServerApplication.class, args);
	}

}

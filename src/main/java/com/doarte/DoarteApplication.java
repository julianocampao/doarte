package com.doarte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DoarteApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoarteApplication.class, args);
	}
}

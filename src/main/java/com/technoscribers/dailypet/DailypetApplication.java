package com.technoscribers.dailypet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailypetApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailypetApplication.class, args);
	}

}

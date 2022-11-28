package com.DavideDalSanto.GTUser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class GtUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtUserApplication.class, args);
		System.out.println("CHIEDIMI SE SONO FELICE");
	}

}

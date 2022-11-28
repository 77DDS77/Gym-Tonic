package com.DavideDalSanto.GTModels;

import com.DavideDalSanto.GTModels.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class GtModelsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtModelsApplication.class, args);
		System.out.println("CHIEDIMI SE SONO FELICE");
	}

}

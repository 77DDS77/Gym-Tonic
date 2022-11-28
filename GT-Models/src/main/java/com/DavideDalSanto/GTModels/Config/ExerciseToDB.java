package com.DavideDalSanto.GTModels.Config;

import com.DavideDalSanto.GTModels.Services.ExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@Component
public class ExerciseToDB implements CommandLineRunner {

    @Autowired
    ExerciseService es;

    @Override
    public void run(String... args) throws Exception {
        log.info("START");
//        getExAPI();
        log.info("END");

    }

    private void getExAPI(){
        try {
            es.getFromApiWhitParams("muscle", "abdominals");
            es.getFromApiWhitParams("muscle", "abductors");
            es.getFromApiWhitParams("muscle", "adductors");
            es.getFromApiWhitParams("muscle", "biceps");
            es.getFromApiWhitParams("muscle", "calves");
            es.getFromApiWhitParams("muscle", "chest");
            es.getFromApiWhitParams("muscle", "forearms");
            es.getFromApiWhitParams("muscle", "glutes");
            es.getFromApiWhitParams("muscle", "hamstrings");
            es.getFromApiWhitParams("muscle", "lats");
            es.getFromApiWhitParams("muscle", "lower_back");
            es.getFromApiWhitParams("muscle", "middle_back");
            es.getFromApiWhitParams("muscle", "neck");
            es.getFromApiWhitParams("muscle", "quadriceps");
            es.getFromApiWhitParams("muscle", "traps");
            es.getFromApiWhitParams("muscle", "triceps");


        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

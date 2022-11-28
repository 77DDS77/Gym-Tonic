package com.DavideDalSanto.GTModels.Services;

import com.DavideDalSanto.GTModels.Entities.Exercise;
import com.DavideDalSanto.GTModels.Exceptions.ExerciseNameNotFoundException;
import com.DavideDalSanto.GTModels.Repositories.ExerciseRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ch.qos.logback.core.joran.spi.ConsoleTarget.findByName;

@Service
@Slf4j
public class ExerciseService {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    ExerciseRepository er;

    /**
    * Saves an Exercise found in the API only if it doesn't exist in the DB already
     *  */
    public void save(Exercise exercise){
        Optional<Exercise> found = er.findByName(exercise.getName());
        if(found.isEmpty()){
            er.save(exercise);
        }
        log.info("Already in DB");
    }

    /**
     * Convert the object found in the API to an Exercise Entity
     *  */
    private Exercise ExerciseConverter(Object o){
        String exStr = o.toString();
        String[] split = exStr.split(",");
        Exercise apiEx = new Exercise();
        apiEx.setName(split[0].split("=")[1]);
        apiEx.setType(split[1].split("=")[1]);
        apiEx.setMuscle(split[2].split("=")[1]);
        apiEx.setEquipment(split[3].split("=")[1]);
        apiEx.setDifficulty(split[4].split("=")[1]);
        apiEx.setInstructions(split[5].split("=")[1]);
        System.out.println(apiEx.toString() + "\n");
        return apiEx;
    }

    /**
     * ONLY USED FOR TESTING, NOT MEANT TO BE USED
     *  */
    private List<Exercise> getAllFromAPI() throws URISyntaxException, IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();
        List<?> exList = new ArrayList<>();

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.api-ninjas.com/v1/exercises"))
                .header("x-api-key", apiKey)
                .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(getResponse.body());
        exList = gson.fromJson(getResponse.body(), List.class);

        List<Exercise> res = new ArrayList<>();

        for (Object o : exList) {
            Exercise converted = ExerciseConverter(o);
            res.add(converted);
        }
        return res;
    }

    /**
     * Gets from the API the firs 10 exercises found that matches the given params.
     * ONLY USED TO POPULATE THE DB AS FOR NOW
     *  */
    public List<Exercise> getFromApiWhitParams(String param, String value) throws URISyntaxException, IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        Gson gson = new Gson();
        List<?> exList;

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI("https://api.api-ninjas.com/v1/exercises?" + param + "=" + value))
                .header("x-api-key", apiKey)
                .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(getResponse.body());
        exList = gson.fromJson(getResponse.body(), List.class);

        List<Exercise> res = new ArrayList<>();

        for (Object o : exList) {
            Exercise converted = ExerciseConverter(o);
            res.add(converted);
            save(converted);
        }
        return res;
    }

    /**
    * Return all Exercises from the DB*/
    public List<Exercise> getAllFromDb(){
        return er.findAll();
    }

    /**
     * Return a page of Exercises from the DB*/
    public Page<Exercise> getAllFromDbPageable(Pageable p){
        return er.findAll(p);
    }

    /**
    * Get a single exercise searching by name.
     * NB: not sure if I need itn might change to return a List if the name is partial*/
    public Exercise getByName(String name) throws ExerciseNameNotFoundException {
        Optional<Exercise> found =  er.findByName(name);
        if(found.isPresent()){
            return found.get();
        }
        throw new ExerciseNameNotFoundException(name);
    }

    /**
    * Returns a List of exercise containing the string passed*/
    public List<Exercise> getByNameOrPartial(String name){
        return er.findByNameContains(name);
    }

    /**
     * Return the list of all exercises specific for that muscle.
     * Get data from DB not API.
     * NB: need it for a select input.*/
    public List<Exercise> getByMuscle(String muscle){
        return er.findByMuscle(muscle);
    }

    /*
    * No Update or delete
    * */

}

package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.DTO.ExerciseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class ExerciseService {

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * GET all the exercises associated with the given GTUser
     * */
    public List<ExerciseDTO> getExNameByMuscle(String muscle) throws URISyntaxException, IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:9090/GT/exercises/muscle/" + muscle))
                .build();

        HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        List<ExerciseDTO> res = objectMapper.readValue(
                postResponse.body(),
                objectMapper.getTypeFactory().constructCollectionLikeType(List.class, ExerciseDTO.class));

        System.out.println("EUREKA");
        return res;
    }

}

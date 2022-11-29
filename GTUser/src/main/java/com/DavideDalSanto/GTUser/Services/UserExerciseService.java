package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.JWTUser;
import com.DavideDalSanto.GTUser.Repositories.GTUserRepository;
import com.DavideDalSanto.GTUser.Repositories.JWTUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class UserExerciseService {

    @Autowired
    private JWTUserRepository jr;

    ObjectMapper objectMapper = new ObjectMapper();


    /**
     * GET all the exercises associated with the given GTUser
     * */
    public List<UserExerciseDTO> getUserExercises(Long GTUserID) throws IOException, URISyntaxException, InterruptedException {
        Optional<JWTUser> found = jr.findById(GTUserID);
        if(found.isPresent()){
            List<Long> userEx = found.get().getUserExercisesId();
            String stringedExIds = objectMapper.writeValueAsString(userEx);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/user-exercises/my-exercises")) //TODO metti url giusto
                    .POST(HttpRequest.BodyPublishers.ofString(stringedExIds))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            List<UserExerciseDTO> res = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructCollectionLikeType(List.class, UserExerciseDTO.class));

            System.out.println("EUREKA");
            return res;
        }
        return null;
    }

    /**
     * POST a new UserExercise on the Models Server,
     * updates the given GTUser's exercise List.
     * */
    public UserExerciseDTO postNewUserExercise(Long GTUserID, UserExerciseDTO exercise) throws IOException, URISyntaxException, InterruptedException {
        Optional<JWTUser> found = jr.findById(GTUserID);
        if(found.isPresent()){
            String stringedExercise = objectMapper.writeValueAsString(exercise);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/user-exercises/new-exercise"))
                    .POST(HttpRequest.BodyPublishers.ofString(stringedExercise))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            UserExerciseDTO newEx = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructType(UserExerciseDTO.class));

            found.get().getUserExercisesId().add(newEx.getId());
            jr.save(found.get());
            return newEx;
        }
        return null;
    }

    /**
     * PUT -
     * Update a modified UserExercise
     * */
    public UserExerciseDTO updateUserExercise(Long GTUserID, UserExerciseDTO updated) throws IOException, URISyntaxException, InterruptedException {
        Optional<JWTUser> found = jr.findById(GTUserID);
        if(found.isPresent()){
            String stringedExercise = objectMapper.writeValueAsString(updated);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/user-exercises/new-exercise"))
                    .POST(HttpRequest.BodyPublishers.ofString(stringedExercise))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructType(UserExerciseDTO.class));
        }
        return null;
    }

    /**
     * DELETE -
     * Given the GTUser ID and the UserExercise ID checks
     * if the user exist and if the given UserExercise
     * exists in his profile, if both true deletes the
     * UserExercise and update the GTUser profile
     * */
    public String deleteExercise(Long GTUserID, Long exID) throws IOException, URISyntaxException, InterruptedException {
        Optional<JWTUser> found = jr.findById(GTUserID);
        if(found.isPresent()){
            if(found.get().getUserExercisesId().contains(exID)){
                String stringedEx = objectMapper.writeValueAsString(exID);

                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:9090/GT/user-exercises/delete-exercise"))
                        .POST(HttpRequest.BodyPublishers.ofString(stringedEx))
                        .build();

                HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

                found.get().getUserExercisesId().remove(exID);
                jr.save(found.get());
                return postResponse.body();
            }
            return "User ("+ GTUserID + ") have no Exercise with id (" + exID + ").";
        }
        return "User ("+ GTUserID + ") not found";
    }
}

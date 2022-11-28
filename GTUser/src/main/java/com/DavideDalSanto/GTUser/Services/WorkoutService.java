package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.DTO.WorkoutDTO;
import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Repositories.GTUserRepository;
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
public class WorkoutService {

    @Autowired
    private GTUserRepository ur;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Given the GTUser ID find in the models Server his workouts
     * and returns them.
     * */
    public List<WorkoutDTO> getUserWorkouts(Long GTUserID) throws IOException, InterruptedException, URISyntaxException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            List<Long> userWorkouts = found.get().getUserWorkoutsId();
            String workoutIds = objectMapper.writeValueAsString(userWorkouts);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/workouts/user-workouts")) //TODO mettere url
                    .POST(HttpRequest.BodyPublishers.ofString(workoutIds))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            List<WorkoutDTO> res = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructCollectionLikeType(List.class, WorkoutDTO.class));

            System.out.println("EUREKA");
            return res;
        }
        return null;
    }

    /**
     * Post a new Workout on the Models Server,
     * updates the given GTUser's plans List.
     * */
    public WorkoutDTO postNewUserWorkout(Long GTUserID, WorkoutDTO workout) throws IOException, InterruptedException, URISyntaxException {

        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            String stringedWorkout = objectMapper.writeValueAsString(workout);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/workouts/new-workout"))
                    .POST(HttpRequest.BodyPublishers.ofString(stringedWorkout))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            WorkoutDTO newWorkout = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructType(WorkoutDTO.class));

            found.get().getUserWorkoutsId().add(newWorkout.getId());
            ur.save(found.get());
            return newWorkout;
        }
        return null;
    }

    /**
     * Given the GTUser ID and the Workout ID checks
     * if the user exist and if the given Workout
     * exists in his profile, if both true deletes the
     * Workout and update the GTUser profile
     * */
    public String deleteWorkout(Long GTUserID, Long workoutID) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            if(found.get().getUserWorkoutsId().contains(workoutID)){
                String stringedEWorkout= objectMapper.writeValueAsString(workoutID);

                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:9090/GT/workouts/delete-workout"))
                        .POST(HttpRequest.BodyPublishers.ofString(stringedEWorkout))
                        .build();

                HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

                found.get().getUserWorkoutsId().remove(workoutID);
                ur.save(found.get());
                return postResponse.body();
            }
            return "User ("+ GTUserID + ") have no Workout with id (" + workoutID + ").";
        }
        return "User ("+ GTUserID + ") not found";
    }

    /**
     * PUT -
     * Update a modified Workout.
     * */
    public WorkoutDTO updateWorkout(Long GTUserID, WorkoutDTO updated) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            String stringedWorkout = objectMapper.writeValueAsString(updated);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/workouts/new-workout"))
                    .POST(HttpRequest.BodyPublishers.ofString(stringedWorkout))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            return objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructType(WorkoutDTO.class));
        }
        return null;
    }


}

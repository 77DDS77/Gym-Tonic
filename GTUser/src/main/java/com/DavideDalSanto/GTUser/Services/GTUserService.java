package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.DTO.ExerciseDTO;
import com.DavideDalSanto.GTUser.DTO.PlanDTO;
import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.DTO.WorkoutDTO;
import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Exceptions.GTUserIdException;
import com.DavideDalSanto.GTUser.Repositories.GTUserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Slf4j
public class GTUserService {

    @Autowired
    private GTUserRepository ur;

    @Autowired
    PasswordEncoder encoder;

    public void save(GTUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        ur.save(user);
    }

//    public GTUser save(GTUser gtUser){ return ur.save(gtUser); }

    public GTUser update(GTUser gtUser){
        if(gtUser.getId() != null){
            return ur.save(gtUser);
        }else{
            throw new IllegalStateException("No id found.");
        }
    }

    public String delete(Long id) throws GTUserIdException {
        if(ur.findById(id).isPresent()) {
            ur.delete(ur.findById(id).get());
            return "User deleted.";
        }else{
            throw new GTUserIdException(id);
        }
    }

    public List<GTUser> getAll(){return ur.findAll();}

    public Page<GTUser> getAllPaginate(Pageable p) {
        return ur.findAll(p);
    }

    /*
    * Trying not to blow everything up
    * */

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Post a new UserExercise on the Models Server,
     * updates the given GTUser's exercise List.
     * */
    public UserExerciseDTO postNewUserExercise(Long GTUserID, UserExerciseDTO exercise) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
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
            save(found.get());
            return newEx;
        }
        return null;
    }

    public List<UserExerciseDTO> getUserExercises(Long GTUserID) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
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
    * Given the GTUser ID find in the models Server his plans
     * and returns them.
     * */
    public List<PlanDTO> getPlan(Long GTUserID) throws IOException, InterruptedException, URISyntaxException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            List<Long> userPlans = found.get().getUserPlansIds();
            String plansIds = objectMapper.writeValueAsString(userPlans);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/plans/user-plans"))
                    .POST(HttpRequest.BodyPublishers.ofString(plansIds))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            List<PlanDTO> res = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructCollectionLikeType(List.class, PlanDTO.class));

            System.out.println("EUREKA");
            return res;
        }
        return null;
    }

    /**
     * Post a new Plan on the Models Server,
     * updates the given GTUser's plans List.
     * */
    public PlanDTO postNewPlan(Long GTUserID, PlanDTO plan) throws IOException, InterruptedException, URISyntaxException {

        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            String stringedPlan = objectMapper.writeValueAsString(plan);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/plans/new-plan"))
                    .POST(HttpRequest.BodyPublishers.ofString(stringedPlan))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            PlanDTO newPlan = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructType(PlanDTO.class));

            found.get().getUserPlansIds().add(newPlan.getId());
            save(found.get());
            return newPlan;
        }
        return null;
    }

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
            save(found.get());
            return newWorkout;
        }
        return null;
    }

    /**
    * Given the GTUser ID and the UserExercise ID checks
     * if the user exist and if the given UserExercise
     * exists in his profile, if both true deletes the
     * UserExercise and update the GTUser profile
     * */
    public String deleteExercise(Long GTUserID, Long exID) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
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
                save(found.get());
                return postResponse.body();
            }
            return "User ("+ GTUserID + ") have no Exercise with id (" + exID + ").";
        }
        return "User ("+ GTUserID + ") not found";
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
                save(found.get());
                return postResponse.body();
            }
            return "User ("+ GTUserID + ") have no Workout with id (" + workoutID + ").";
        }
        return "User ("+ GTUserID + ") not found";
    }

    /**
     * Given the GTUser ID and the Plan ID checks
     * if the user exist and if the given Plan
     * exists in his profile, if both true deletes the
     * Plan and update the GTUser profile
     * */
    public String deletePlan(Long GTUserID, Long planID) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            if(found.get().getUserPlansIds().contains(planID)){
                String stringedPlan= objectMapper.writeValueAsString(planID);

                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:9090/GT/plans/delete-workout")) //TODO mettere url
                        .POST(HttpRequest.BodyPublishers.ofString(stringedPlan))
                        .build();

                HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

                found.get().getUserPlansIds().remove(planID);
                save(found.get());
                return postResponse.body();
            }
            return "User ("+ GTUserID + ") have no Plan with id (" + planID + ").";
        }
        return "User ("+ GTUserID + ") not found";
    }


}


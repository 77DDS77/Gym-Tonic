package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.PlanDTO;
import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.DTO.WorkoutDTO;
import com.DavideDalSanto.GTUser.Services.GTUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/*
* Controller to manage all requests to Models microservice
* */

@Slf4j
@RestController
@RequestMapping("/GT")
public class ActionController {

    @Autowired
    private GTUserService us;

//    /**
//     * Gets all the exercises the given GTUser
//     * has saved in his profile.
//     * */
//    @PostMapping("users/{id}/exercises")
//    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
//    public ResponseEntity<List<UserExerciseDTO>> getUserExercises(@PathVariable(name = "id") Long id){
//        try{
//            return new ResponseEntity<List<UserExerciseDTO>>(us.getUserExercises(id), HttpStatus.OK);
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * Post a new UserExercise on the Models Server,
//     * updates the given GTUser's exercise List.
//     * */
//    @PostMapping("users/{id}/new-exercise")
//    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
//    public ResponseEntity<UserExerciseDTO> postNewExercise(@PathVariable(name = "id") Long id, @RequestBody UserExerciseDTO exercise){
//        try{
//            return new ResponseEntity<>(us.postNewUserExercise(id, exercise), HttpStatus.OK);
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }


//    /**
//     * Gets all the exercises the given GTUser
//     * has saved in his profile.
//     * */
//    @PostMapping("users/{id}/workouts")
//    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
//    public ResponseEntity<List<WorkoutDTO>> getUserWorkout(@PathVariable(name = "id") Long id){
//        try{
//            return new ResponseEntity<List<WorkoutDTO>>(us.getUserWorkouts(id), HttpStatus.OK);
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * Post a new UserExercise on the Models Server,
//     * updates the given GTUser's exercise List.
//     * */
//    @PostMapping("users/{id}/new-workout")
//    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
//    public ResponseEntity<WorkoutDTO> postNewWorkout(@PathVariable(name = "id") Long id, @RequestBody WorkoutDTO workout){
//        try{
//            return new ResponseEntity<>(us.postNewUserWorkout(id, workout), HttpStatus.OK);
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }



//    /**
//    * Given the id of a GTUSER gets from
//     * the Models microservice his plans
//     * */
//    @PostMapping("users/{id}/plans")
//    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
//    public ResponseEntity<List<PlanDTO>> getUserPlans(@PathVariable(name = "id") Long id){
//        try{
//            return new ResponseEntity<List<PlanDTO>>(us.getPlan(id), HttpStatus.OK);
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    /**
//     * Post a new Plan on the Models Server,
//     * updates the given GTUser's plan List.
//     * */
//    @PostMapping("users/{id}/new-plan")
//    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
//    public ResponseEntity<PlanDTO> postNewPlan(@PathVariable(name = "id") Long GTUserID, @RequestBody PlanDTO plan){
//        try{
//            return new ResponseEntity<>(us.postNewPlan(GTUserID, plan), HttpStatus.OK);
//        } catch (IOException | URISyntaxException | InterruptedException e) {
//            log.error(e.getMessage(), e);
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
}

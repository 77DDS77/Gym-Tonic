package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.DTO.WorkoutDTO;
import com.DavideDalSanto.GTUser.Services.GTUserService;
import com.DavideDalSanto.GTUser.Services.WorkoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/GT")
public class WorkoutController {

    @Autowired
    private GTUserService us;

    @Autowired
    WorkoutService ws;

    /**
     * Gets all the exercises the given GTUser
     * has saved in his profile.
     * */
    @PostMapping("users/{id}/workouts")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<List<WorkoutDTO>> getUserWorkout(@PathVariable(name = "id") Long id){
        try{
            return new ResponseEntity<List<WorkoutDTO>>(ws.getUserWorkouts(id), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Post a new UserExercise on the Models Server,
     * updates the given GTUser's exercise List.
     * */
    @PostMapping("users/{id}/new-workout")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<WorkoutDTO> postNewWorkout(@PathVariable(name = "id") Long id, @RequestBody WorkoutDTO workout){
        try{
            return new ResponseEntity<>(ws.postNewUserWorkout(id, workout), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete the given Workout from the user profile
     * */
    @PostMapping("users/{id}/delete-workout/{wID}")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<String> deleteWorkout(@PathVariable(name = "id") Long GTUserid, @PathVariable(name = "wID") Long workoutID){
        try{
            return new ResponseEntity<>(ws.deleteWorkout(GTUserid, workoutID), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("users/{id}/update-workout")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<WorkoutDTO> updateWorkout(@PathVariable(name = "id") Long GTUserid, @RequestBody WorkoutDTO updated){
        try{
            return new ResponseEntity<>(ws.updateWorkout(GTUserid, updated), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

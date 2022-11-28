package com.DavideDalSanto.GTModels.Controllers;

import com.DavideDalSanto.GTModels.Entities.Plan;
import com.DavideDalSanto.GTModels.Entities.UserExercise;
import com.DavideDalSanto.GTModels.Entities.Workout;
import com.DavideDalSanto.GTModels.Exceptions.UserExerciseIdException;
import com.DavideDalSanto.GTModels.Exceptions.WorkoutIdException;
import com.DavideDalSanto.GTModels.Services.WorkoutService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/GT/workouts")
public class WorkoutController {

    @Autowired
    WorkoutService ws;

    /*
     * getAllUserExercises()
     * getPageableUserExercise()
     * probabilmente non verranno mai usati*/

    @GetMapping("/all")
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return new ResponseEntity<>(ws.getAll(), HttpStatus.OK);
    }

    @GetMapping("/all-page")
    public ResponseEntity<Page<Workout>> getPageableWorkouts(Pageable p){
        Page<Workout> foundAll = ws.getAllPaginate(p);
        if(foundAll.hasContent()){
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Workout> saveNewWorkout(@RequestBody Workout workout){
        try{
            return new ResponseEntity<>(ws.save(workout), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Workout> updateWorkout(@RequestBody Workout workout){
        try{
            return new ResponseEntity<>(ws.update(workout), HttpStatus.OK);
        }catch(IllegalStateException ise){
            log.error(ise.getMessage(), ise);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserExercise(@RequestBody Workout workout){
        try{
            return new ResponseEntity<>(ws.delete(workout.getId()), HttpStatus.OK);
        } catch (WorkoutIdException e) {
            throw new RuntimeException(e);
        }
    }


    /*
     * Microservices stuff.
     * */

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Gets a string of Ids, convert them in a List<Long> and feeds
     * them to the getUserWorkout(convIDs) method.
     * */
    @PostMapping("/user-workouts")
    public ResponseEntity<List<Workout>> getUserWorkout(@RequestBody String userPlansIds) throws JsonProcessingException {

        List<Long> convIDs = objectMapper.readValue(
                userPlansIds,
                objectMapper.getTypeFactory().constructCollectionLikeType(List.class, Long.class));

        return new ResponseEntity<>(ws.getUserWorkouts(convIDs), HttpStatus.OK);
    }

    /**
     * Used in GTUsers, receives a stringed Workout
     * Object converts it in a POJO, saves it in the DB.
     * */
    @PostMapping("/new-workout")
    public ResponseEntity<Workout> NewWorkoutFromMicro(@RequestBody String stringWorkout){
        try{
            Workout newWorkout = objectMapper.readValue(
                    stringWorkout,
                    objectMapper.getTypeFactory().constructType(Workout.class)
            );
            return new ResponseEntity<>(ws.save(newWorkout), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Used in GTUsers, receives a stringed ID,
     * deletes the corresponding Workout
     * */
    @PostMapping("/delete-workout")
    public ResponseEntity<String> deleteWorkout(@RequestBody String workoutIds) throws JsonProcessingException{

        Long convIDs = objectMapper.readValue(
                workoutIds,
                objectMapper.getTypeFactory().constructType(Long.class));

        try {
            return new ResponseEntity<>(ws.delete(convIDs), HttpStatus.OK);
        } catch (WorkoutIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}

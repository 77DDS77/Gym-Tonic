package com.DavideDalSanto.GTModels.Controllers;

import com.DavideDalSanto.GTModels.Entities.Exercise;
import com.DavideDalSanto.GTModels.Entities.Plan;
import com.DavideDalSanto.GTModels.Entities.UserExercise;
import com.DavideDalSanto.GTModels.Exceptions.UserExerciseIdException;
import com.DavideDalSanto.GTModels.Services.UserExerciseService;
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
@RequestMapping("/GT/user-exercises")
public class UserExerciseController {

    @Autowired
    UserExerciseService ues;

    /*
    * getAllUserExercises()
    * getPageableUserExercise()
    * probabilmente non verranno mai usati*/

    @GetMapping("/all")
    public ResponseEntity<List<UserExercise>> getAllUserExercises() {
        return new ResponseEntity<>(ues.getAll(), HttpStatus.OK);
    }

    @GetMapping("/all-page")
    public ResponseEntity<Page<UserExercise>> getPageableUserExercise(Pageable p){
        Page<UserExercise> foundAll = ues.getAllPaginate(p);
        if(foundAll.hasContent()){
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<UserExercise> saveNewUserExercise(@RequestBody UserExercise userExercise){
        try{
            return new ResponseEntity<>(ues.save(userExercise), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<UserExercise> updateUserExercise(@RequestBody UserExercise userExercise){
        try{
            return new ResponseEntity<>(ues.update(userExercise), HttpStatus.OK);
        }catch(IllegalStateException ise){
            log.error(ise.getMessage(), ise);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserExercise(@RequestBody UserExercise userExercise){
        try{
            return new ResponseEntity<>(ues.delete(userExercise.getId()), HttpStatus.OK);
        } catch (UserExerciseIdException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //-------------------- ENDPOINTS FOR MICRO TALKING

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Used in GTUsers, receives a stringed UserExercise
     * Object converts it in a POJO, saves it in the DB.
     * */
    @PostMapping("/new-exercise")
    public ResponseEntity<UserExercise> NewUserExerciseFromMicro(@RequestBody String stringUserExercise){
        try{
            UserExercise userExercise = objectMapper.readValue(
                    stringUserExercise,
                    objectMapper.getTypeFactory().constructType(UserExercise.class)
            );
            return new ResponseEntity<>(ues.save(userExercise), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Used in GTUsers, receives a stringed list of id's,
     * gets in response the corresponding UserExercise fetched
     * from the Models DB
     * */
    @PostMapping("/my-exercises")
    public ResponseEntity<List<UserExercise>> getUserExercises(@RequestBody String userExercisesIds) throws JsonProcessingException {

        List<Long> convIDs = objectMapper.readValue(
                userExercisesIds,
                objectMapper.getTypeFactory().constructCollectionLikeType(List.class, Long.class));

        return new ResponseEntity<>(ues.getUserExercises(convIDs), HttpStatus.OK);
    }

    /**
     * Used in GTUsers, receives a stringed ID,
     * deletes the corresponding userExercise
     * */
    @PostMapping("/delete-exercise")
    public ResponseEntity<String> deleteUserExercise(@RequestBody String userExerciseIds) throws JsonProcessingException{

        Long convIDs = objectMapper.readValue(
                userExerciseIds,
                objectMapper.getTypeFactory().constructType(Long.class));

        try {
            return new ResponseEntity<>(ues.delete(convIDs), HttpStatus.OK);
        } catch (UserExerciseIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}

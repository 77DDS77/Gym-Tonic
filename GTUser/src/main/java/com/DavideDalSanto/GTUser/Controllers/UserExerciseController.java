package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.Services.GTUserService;
import com.DavideDalSanto.GTUser.Services.UserExerciseService;
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
@CrossOrigin("http://localhost:4200")
public class UserExerciseController {

    @Autowired
    private UserExerciseService ues;

    /**
     * Gets all the exercises the given GTUser
     * has saved in his profile.
     * */
    @GetMapping("{userId}/exercises")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<List<UserExerciseDTO>> getUserExercises(@PathVariable(name = "userId") Long id){
        try{
            return new ResponseEntity<List<UserExerciseDTO>>(ues.getUserExercises(id), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Post a new UserExercise on the Models Server,
     * updates the given GTUser's exercise List.
     * */
    @PostMapping("{userId}/new-exercise")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<UserExerciseDTO> postNewExercise(@PathVariable(name = "userId") Long id, @RequestBody UserExerciseDTO exercise){
        try{
            return new ResponseEntity<>(ues.postNewUserExercise(id, exercise), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete the given exercise from the user profile
     * */
    @DeleteMapping("{userId}/delete-exercise/{exID}")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<String> deleteUserExercise(@PathVariable(name = "userId") Long GTUserid, @PathVariable(name = "exID") Long exID){
        try{
            return new ResponseEntity<>(ues.deleteExercise(GTUserid, exID), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{userId}/update-exercise")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<UserExerciseDTO> updateUserExercise(@PathVariable(name = "userId") Long GTUserid, @RequestBody UserExerciseDTO updated){
        try{
            return new ResponseEntity<>(ues.updateUserExercise(GTUserid, updated), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



}

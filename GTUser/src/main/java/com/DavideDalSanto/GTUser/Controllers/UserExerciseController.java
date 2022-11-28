package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.UserExerciseDTO;
import com.DavideDalSanto.GTUser.Services.GTUserService;
import com.DavideDalSanto.GTUser.Services.UserExerciseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
public class UserExerciseController {

    @Autowired
    private GTUserService us;

    @Autowired
    private UserExerciseService uer;

    /**
     * Gets all the exercises the given GTUser
     * has saved in his profile.
     * */
    @PostMapping("users/{id}/exercises")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<List<UserExerciseDTO>> getUserExercises(@PathVariable(name = "id") Long id){
        try{
            return new ResponseEntity<List<UserExerciseDTO>>(uer.getUserExercises(id), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Post a new UserExercise on the Models Server,
     * updates the given GTUser's exercise List.
     * */
    @PostMapping("users/{id}/new-exercise")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<UserExerciseDTO> postNewExercise(@PathVariable(name = "id") Long id, @RequestBody UserExerciseDTO exercise){
        try{
            return new ResponseEntity<>(uer.postNewUserExercise(id, exercise), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete the given exercise from the user profile
     * */
    @PostMapping("users/{id}/delete-exercise/{exID}")
    public ResponseEntity<String> deleteUserExercise(@PathVariable(name = "id") Long GTUserid, @PathVariable(name = "exID") Long exID){
        try{
            return new ResponseEntity<>(uer.deleteExercise(GTUserid, exID), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



}

package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.ExerciseDTO;
import com.DavideDalSanto.GTUser.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/GT")
@CrossOrigin("http://localhost:4200")
public class ExerciseController {

    @Autowired
    ExerciseService es;

    @GetMapping("/exercise/{muscle}")
    public ResponseEntity<List<ExerciseDTO>> getExNameByMuscle(@PathVariable(name = "muscle") String muscle){
        try{
            return new ResponseEntity<>(es.getExNameByMuscle(muscle), HttpStatus.OK);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.DavideDalSanto.GTModels.Controllers;

import com.DavideDalSanto.GTModels.Entities.Exercise;
import com.DavideDalSanto.GTModels.Services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/GT/exercises")
public class ExerciseController {

    @Autowired
    ExerciseService es;

    @GetMapping("/all")
    public ResponseEntity<List<Exercise>> getAllFromDB(){
        return new ResponseEntity<>(es.getAllFromDb(), HttpStatus.OK);
    }

    @GetMapping("/all-page")
    public ResponseEntity<Page<Exercise>> getPageableExercise(Pageable p){
        Page<Exercise> foundAll = es.getAllFromDbPageable(p);
        if(foundAll.hasContent()){
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Exercise>> getByNameOrPartial(@PathVariable(name = "name") String name){
        return new ResponseEntity<>(es.getByNameOrPartial(name),HttpStatus.OK) ;
    }

    /**
    * Endpoint for select Input*/
    @GetMapping("/muscle/{muscle}")
    public ResponseEntity<List<Exercise>> getByMuscle(@PathVariable(name="muscle") String muscle){
        return new ResponseEntity<>(es.getByMuscle(muscle), HttpStatus.OK);
    }


}

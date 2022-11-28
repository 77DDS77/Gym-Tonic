package com.DavideDalSanto.GTModels.Controllers;

import com.DavideDalSanto.GTModels.Entities.Plan;
import com.DavideDalSanto.GTModels.Entities.UserExercise;
import com.DavideDalSanto.GTModels.Exceptions.PlanIdException;
import com.DavideDalSanto.GTModels.Exceptions.UserExerciseIdException;
import com.DavideDalSanto.GTModels.Exceptions.WorkoutIdException;
import com.DavideDalSanto.GTModels.Services.PlanService;
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
@RequestMapping("/GT/plans")
public class PlanController {

    @Autowired
    PlanService ps;

    /*
     * getAllPlans()
     * getPageablePlans()
     * probabilmente non verranno mai usati*/

    @GetMapping("/all")
    public ResponseEntity<List<Plan>> getAllPlans() {
        return new ResponseEntity<>(ps.getAll(), HttpStatus.OK);
    }

    @GetMapping("/all-page")
    public ResponseEntity<Page<Plan>> getPageablePlans(Pageable p){
        Page<Plan> foundAll = ps.getAllPaginate(p);
        if(foundAll.hasContent()){
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Plan> saveNewUserExercise(@RequestBody Plan plan){
        try{
            return new ResponseEntity<>(ps.save(plan), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Plan> updateUserExercise(@RequestBody Plan plan){
        try{
            return new ResponseEntity<>(ps.update(plan), HttpStatus.OK);
        }catch(IllegalStateException ise){
            log.error(ise.getMessage(), ise);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserExercise(@RequestBody Plan plan){
        try{
            return new ResponseEntity<>(ps.delete(plan.getId()), HttpStatus.OK);
        } catch (PlanIdException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Microservices stuff.
    * */

    ObjectMapper objectMapper = new ObjectMapper();

    /**
    * Gets a string of Ids, convert them in a List<Long> and feeds
     * them to the getUserPlans(convIDs) method.
    * */
    @PostMapping("/user-plans")
    public ResponseEntity<List<Plan>> getUserPlans(@RequestBody String userPlansIds) throws JsonProcessingException {

        List<Long> convIDs = objectMapper.readValue(
                userPlansIds,
                objectMapper.getTypeFactory().constructCollectionLikeType(List.class, Long.class));

        return new ResponseEntity<>(ps.getUserPlans(convIDs), HttpStatus.OK);
    }

    /**
     * Used in GTUsers, receives a stringed Plan
     * Object converts it in a POJO, saves it in the DB.
     * */
    @PostMapping("/new-plan")
    public ResponseEntity<Plan> NewPlanFromMicro(@RequestBody String stringPlan){
        try{
            Plan newPlan = objectMapper.readValue(
                    stringPlan,
                    objectMapper.getTypeFactory().constructType(Plan.class)
            );
            return new ResponseEntity<>(ps.save(newPlan), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Used in GTUsers, receives a stringed ID,
     * deletes the corresponding Plan
     * */
    @PostMapping("/delete-workout")
    public ResponseEntity<String> deletePlan(@RequestBody String planId) throws JsonProcessingException {

        Long convIDs = objectMapper.readValue(
                planId,
                objectMapper.getTypeFactory().constructType(Long.class));
        try {
            return new ResponseEntity<>(ps.delete(convIDs), HttpStatus.OK);
        } catch (PlanIdException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}








































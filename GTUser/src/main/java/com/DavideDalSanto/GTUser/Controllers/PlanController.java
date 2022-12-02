package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.PlanDTO;
import com.DavideDalSanto.GTUser.DTO.WorkoutDTO;
import com.DavideDalSanto.GTUser.Services.GTUserService;
import com.DavideDalSanto.GTUser.Services.PlanService;
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
@CrossOrigin(origins = "http://localhost:4200")
public class PlanController {

    @Autowired
    private PlanService ps;

    /**
     * Given the id of a GTUSER gets from
     * the Models microservice his plans
     * */
    @GetMapping("{userId}/plans")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<List<PlanDTO>> getUserPlans(@PathVariable(name = "userId") Long id){
        try{
            return new ResponseEntity<List<PlanDTO>>(ps.getPlan(id), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Post a new Plan on the Models Server,
     * updates the given GTUser's plan List.
     * */
    @PostMapping("{userId}/new-plan")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<PlanDTO> postNewPlan(@PathVariable(name = "userId") Long GTUserID, @RequestBody PlanDTO plan){
        try{
            return new ResponseEntity<>(ps.postNewPlan(GTUserID, plan), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Delete the given Plan from the user profile
     * */
    @PostMapping("{userId}/delete-plan/{pID}")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<String> deletePlan(@PathVariable(name = "userId") Long GTUserid, @PathVariable(name = "pID") Long planID){
        try{
            return new ResponseEntity<>(ps.deletePlan(GTUserid, planID), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("{userId}/update-plan")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<PlanDTO> updatePlan(@PathVariable(name = "userId") Long GTUserid, @RequestBody PlanDTO updated){
        try{
            return new ResponseEntity<>(ps.updateWorkout(GTUserid, updated), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.PlanDTO;
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
public class PlanController {

    @Autowired
    private GTUserService us;

    @Autowired
    private PlanService ps;

    /**
     * Given the id of a GTUSER gets from
     * the Models microservice his plans
     * */
    @PostMapping("users/{id}/plans")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<List<PlanDTO>> getUserPlans(@PathVariable(name = "id") Long id){
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
    @PostMapping("users/{id}/new-plan")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<PlanDTO> postNewPlan(@PathVariable(name = "id") Long GTUserID, @RequestBody PlanDTO plan){
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
    @PostMapping("users/{id}/delete-plan/{pID}")
    public ResponseEntity<String> deletePlan(@PathVariable(name = "id") Long GTUserid, @PathVariable(name = "pID") Long planID){
        try{
            return new ResponseEntity<>(ps.deletePlan(GTUserid, planID), HttpStatus.OK);
        } catch (IOException | URISyntaxException | InterruptedException e) {
            log.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}

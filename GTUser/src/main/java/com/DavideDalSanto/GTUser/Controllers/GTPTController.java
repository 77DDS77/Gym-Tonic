package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.Entities.GTPersonalTrainer;
import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Exceptions.GTPTIdException;
import com.DavideDalSanto.GTUser.Exceptions.GTUserIdException;
import com.DavideDalSanto.GTUser.Exceptions.NonExistingRoleException;
import com.DavideDalSanto.GTUser.Models.SearchedUser;
import com.DavideDalSanto.GTUser.Services.GTPTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/GT/p-trainers")
@CrossOrigin("http://localhost:4200")
public class GTPTController {

    @Autowired
    private GTPTService pts;

    //--------------------------GET-------------------------

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GTPersonalTrainer>> getAllGTPTrainers(){
        return new ResponseEntity<>(pts.getAll(), HttpStatus.OK);
    }

    @GetMapping("/all-pageable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GTPersonalTrainer>> getAllGTUsers(Pageable p){
        return new ResponseEntity<>(pts.getAllPaginate(p), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<GTPersonalTrainer> getById(@PathVariable(name = "id") Long GTPTid){
        try{
            return new ResponseEntity<>(pts.getById(GTPTid), HttpStatus.OK);
        } catch (GTPTIdException e) {
            log.error((e.getMessage()));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------POST-------------------------

    @PostMapping("/new-pt")
    public ResponseEntity<GTPersonalTrainer> newGTUser(@RequestBody GTPersonalTrainer pt){
        try{
            return new ResponseEntity<>(pts.save(pt), HttpStatus.OK);
        } catch (NonExistingRoleException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------------PUT-------------------------

    @PutMapping("/update-pt")
    @PreAuthorize("hasAnyRole('GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<GTPersonalTrainer> updateUser(@RequestBody GTPersonalTrainer updatedPT){
        try{
            return new ResponseEntity<>(pts.update(updatedPT), HttpStatus.OK);
        } catch (GTPTIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-password")
    @PreAuthorize("hasAnyRole('GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<GTPersonalTrainer> updatePassword(@RequestBody GTPersonalTrainer updatedPT){
        try{
            return new ResponseEntity<>(pts.updatePassword(updatedPT), HttpStatus.OK);
        } catch (GTPTIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------DELETE-------------------------

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestBody GTPersonalTrainer pt){
        try{
            return new ResponseEntity<>(pts.delete(pt.getId()), HttpStatus.OK);
        } catch (GTPTIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------ACTIONS-------------------------
    /**
    * Used on the PT-home search bar to return the
     * searched GTUser
     * */
    @GetMapping("/search-user/{username}")
    @PreAuthorize("hasRole('GTPERSONALTRAINER')")
    public ResponseEntity<List<SearchedUser>> searchUserByUsername(@PathVariable(name="username") String username){
        try{
            return new ResponseEntity<>(pts.searchUserByUsername(username), HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my-users/{ptId}")
    @PreAuthorize("hasRole('GTPERSONALTRAINER')")
    public ResponseEntity<List<SearchedUser>> getFollowedUsers(@PathVariable(name = "ptId") Long ptId){
        try{
            return new ResponseEntity<>(pts.getPtFollowedUsers(ptId), HttpStatus.OK);
        } catch (GTPTIdException | GTUserIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



}

package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Exceptions.GTUserIdException;
import com.DavideDalSanto.GTUser.Exceptions.NonExistingRoleException;
import com.DavideDalSanto.GTUser.Services.GTUserService;
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
@RequestMapping("/GT/users")
@CrossOrigin("http://localhost:4200")
public class GTUserController {

    @Autowired
    private GTUserService us;

    //--------------------------GET-------------------------

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<GTUser>> getAllGTUsers(){
        return new ResponseEntity<>(us.getAll(), HttpStatus.OK);
    }

    @GetMapping("/all-pageable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GTUser>> getAllGTUsers(Pageable p){
        return new ResponseEntity<>(us.getAllPaginate(p), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('GTUSER', 'GTPERSONALTRAINER', 'ADMIN')")
    public ResponseEntity<GTUser> getById(@PathVariable(name = "id") Long GTUserid){
        try{
            return new ResponseEntity<>(us.getById(GTUserid), HttpStatus.OK);
        } catch (GTUserIdException e) {
            log.error((e.getMessage()));
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------POST-------------------------

    @PostMapping("/new-user")
    public ResponseEntity<GTUser> newGTUser(@RequestBody GTUser user){
        try{
            return new ResponseEntity<>(us.save(user), HttpStatus.OK);
        } catch (NonExistingRoleException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------------------PUT-------------------------

    @PutMapping("/update-user")
    @PreAuthorize("hasAnyRole('GTUSER', 'ADMIN')")
    public ResponseEntity<GTUser> updateUser(@RequestBody GTUser updatedUser){
        try{
            return new ResponseEntity<>(us.update(updatedUser), HttpStatus.OK);
        } catch (GTUserIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-password")
    @PreAuthorize("hasAnyRole('GTUSER', 'ADMIN')")
    public ResponseEntity<GTUser> updatePassword(@RequestBody GTUser updatedUser){
        try{
            return new ResponseEntity<>(us.updatePassword(updatedUser), HttpStatus.OK);
        } catch (GTUserIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    //--------------------------DELETE-------------------------

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('GTUSER', 'ADMIN')")
    public ResponseEntity<String> deleteUser(@RequestBody GTUser user){
        try{
            return new ResponseEntity<>(us.delete(user.getId()), HttpStatus.OK);
        } catch (GTUserIdException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}

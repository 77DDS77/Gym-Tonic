package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.Entities.JWTUser;
import com.DavideDalSanto.GTUser.Services.JWTUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/GT")
@CrossOrigin("http://localhost:4200")
public class JWTUserController {

    @Autowired
    JWTUserService jus;

    @GetMapping("/all-users")
    public ResponseEntity<List<JWTUser>> getAllUsers(){
        return new ResponseEntity<>(jus.getAllUsers(), HttpStatus.OK);
    }
}

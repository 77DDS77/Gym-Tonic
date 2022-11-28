package com.DavideDalSanto.GTUser.Controllers;

import com.DavideDalSanto.GTUser.DTO.PlanDTO;
import com.DavideDalSanto.GTUser.Services.GTUserService;
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
@RequestMapping("/GT/users")
public class GTUserController {

    @Autowired
    private GTUserService us;

    //TODO fare Controller per creazione/CRUD GTUser

}

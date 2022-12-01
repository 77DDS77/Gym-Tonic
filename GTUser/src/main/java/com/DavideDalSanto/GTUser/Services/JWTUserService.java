package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.JWTUser;
import com.DavideDalSanto.GTUser.Repositories.JWTUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JWTUserService {

    @Autowired
    private JWTUserRepository sur;

    @Autowired
    PasswordEncoder encoder;

    public void save(JWTUser user) {
        user.setPassword(encoder.encode(user.getPassword()));
        sur.save(user);
    }

    public List<JWTUser> getAllUsers(){
        return sur.findAll();
    }

    public JWTUser getById(Long id){
        Optional<JWTUser> found = sur.findById(id);
        if(found.isPresent()){
            return found.get();
        }
        throw new IllegalStateException();
    }

}

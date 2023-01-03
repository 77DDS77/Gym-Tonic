package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.JWTUser;
import com.DavideDalSanto.GTUser.Exceptions.GTUserIdException;
import com.DavideDalSanto.GTUser.Repositories.JWTUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    /**
     * Checks if the password passed in is the same as the user password
     * stored in the database.
     * This is needed for the change password feature.
     * */
    public boolean checkPassword(Long userId, String inputOldPassword){
        log.error("SERVICE " + inputOldPassword);
        String actualOldPw = getById(userId).getPassword();
        return encoder.matches(inputOldPassword, actualOldPw);
    }

    /**
     * Updates GTUser's password making sure its
     * encoded.
     * */
    public JWTUser updatePassword(Long userId, String newPassword) throws GTUserIdException {
        JWTUser old = getById(userId);
        old.setPassword(encoder.encode(newPassword));
        return sur.save(old);
    }
}

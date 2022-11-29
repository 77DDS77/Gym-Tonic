package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.RoleType;
import com.DavideDalSanto.GTUser.Exceptions.GTUserIdException;
import com.DavideDalSanto.GTUser.Exceptions.NonExistingRoleException;
import com.DavideDalSanto.GTUser.Repositories.GTUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GTUserService {

    @Autowired
    private GTUserRepository ur;

    @Autowired
    private RoleService rs;

    @Autowired
    PasswordEncoder encoder;

    public List<GTUser> getAll(){return ur.findAll();}

    public Page<GTUser> getAllPaginate(Pageable p) {
        return ur.findAll(p);
    }

    /**
     * Get a user by its ID,
     * if not found throws an exception.
     * */
    public GTUser getById(Long id) throws GTUserIdException {
        Optional<GTUser> found = ur.findById(id);
        if(found.isPresent()){
            return found.get();
        }
        throw new GTUserIdException(id);

    }

    /**
     * Save a GTUser in the DB,
     * before saving encode his password and
     * sets its role.
     * */
    public GTUser save(GTUser user) throws NonExistingRoleException {
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(rs.findByRoleType(RoleType.ROLE_GTUSER));
        return ur.save(user);
    }

    /**
     * Updates a GTUser's info except for its
     * password.
     * */
    public GTUser update(GTUser updatedUser) throws GTUserIdException {
        GTUser old = getById(updatedUser.getId());
        old.setName(updatedUser.getName());
        old.setLastname(updatedUser.getLastname());
        old.setUsername(updatedUser.getUsername());
        old.setEmail(updatedUser.getEmail());
        old.setUserExercisesId(updatedUser.getUserExercisesId());
        old.setUserWorkoutsId(updatedUser.getUserWorkoutsId());
        old.setUserPlansIds(updatedUser.getUserPlansIds());

        return ur.save(old);
    }

    /**
     * Updates GTUser's password making sure its
     * encoded.'
     * */
    public GTUser updatePassword(GTUser user) throws GTUserIdException {
        GTUser old = getById(user.getId());
        old.setPassword(encoder.encode(user.getPassword()));
        return ur.save(old);
    }

    public String delete(Long id) throws GTUserIdException {
        if(ur.findById(id).isPresent()) {
            ur.delete(ur.findById(id).get());
            return "User deleted.";
        }else{
            throw new GTUserIdException(id);
        }
    }


}


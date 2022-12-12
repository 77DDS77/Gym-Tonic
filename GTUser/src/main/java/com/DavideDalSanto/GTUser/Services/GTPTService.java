package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.Entities.GTPersonalTrainer;
import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Entities.RoleType;
import com.DavideDalSanto.GTUser.Exceptions.GTPTIdException;
import com.DavideDalSanto.GTUser.Exceptions.NonExistingRoleException;
import com.DavideDalSanto.GTUser.Models.SearchedUser;
import com.DavideDalSanto.GTUser.Repositories.GTPTRepository;
import com.DavideDalSanto.GTUser.Repositories.GTUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GTPTService {

    @Autowired
    private GTPTRepository ptr;

    @Autowired
    private GTUserRepository ur;

    @Autowired
    private RoleService rs;

    @Autowired
    PasswordEncoder encoder;

    public List<GTPersonalTrainer> getAll(){return ptr.findAll();}

    public Page<GTPersonalTrainer> getAllPaginate(Pageable p) {
        return ptr.findAll(p);
    }

    /**
     * Get a Personal Trainer by its ID,
     * if not found throws an exception.
     * */
    public GTPersonalTrainer getById(Long id) throws GTPTIdException {
        Optional<GTPersonalTrainer> found = ptr.findById(id);
        if(found.isPresent()){
            return found.get();
        }
        throw new GTPTIdException(id);

    }

    /**
     * Save a Personal Trainer in the DB,
     * before saving encode his password and
     * sets its roles.
     * */
    public GTPersonalTrainer save(GTPersonalTrainer user) throws NonExistingRoleException {
        user.setPassword(encoder.encode(user.getPassword()));
//        user.addRole(rs.findByRoleType(RoleType.ROLE_GTUSER));
        user.addRole(rs.findByRoleType(RoleType.ROLE_GTPERSONALTRAINER));
        return ptr.save(user);
    }

    /**
     * Updates a Personal Trainer's info except for its
     * password.
     * */
    public GTPersonalTrainer update(GTPersonalTrainer updatedPT) throws GTPTIdException {
        GTPersonalTrainer old = getById(updatedPT.getId());
        old.setName(updatedPT.getName());
        old.setLastname(updatedPT.getLastname());
        old.setUsername(updatedPT.getUsername());
        old.setEmail(updatedPT.getEmail());
        old.setUserExercisesId(updatedPT.getUserExercisesId());
        old.setUserWorkoutsId(updatedPT.getUserWorkoutsId());
        old.setUserPlansIds(updatedPT.getUserPlansIds());
        old.setGtUserIds(updatedPT.getGtUserIds());

        return ptr.save(old);
    }

    /**
     * Updates a Personal Trainer's password making sure its
     * encoded.
     * */
    public GTPersonalTrainer updatePassword(GTPersonalTrainer user) throws GTPTIdException {
        GTPersonalTrainer old = getById(user.getId());
        old.setPassword(encoder.encode(user.getPassword()));
        return ptr.save(old);
    }

    public String delete(Long id) throws GTPTIdException {
        if(ptr.findById(id).isPresent()) {
            ptr.delete(ptr.findById(id).get());
            return "Personal Trainer deleted.";
        }else{
            throw new GTPTIdException(id);
        }
    }

    //PT Actions

    public List<SearchedUser> searchUserByUsername(String username){
        List<GTUser>  users = ur.findByUsernameContainsIgnoreCase(username);
        List<SearchedUser> res = new ArrayList<SearchedUser>();

        for(GTUser user : users){
            SearchedUser found = new SearchedUser();
            found.setId(user.getId());
            found.setUsername(user.getUsername());
            found.setName(user.getName());
            found.setLastname(user.getLastname());
            found.setUserExercisesId(user.getUserExercisesId());
            found.setUserWorkoutsId(user.getUserWorkoutsId());
            found.setUserPlansIds(user.getUserPlansIds());
            res.add(found);
        }
        return res;
    }
}

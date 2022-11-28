package com.DavideDalSanto.GTModels.Services;

import com.DavideDalSanto.GTModels.Entities.Plan;
import com.DavideDalSanto.GTModels.Entities.UserExercise;
import com.DavideDalSanto.GTModels.Exceptions.UserExerciseIdException;
import com.DavideDalSanto.GTModels.Repositories.UserExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserExerciseService {

    @Autowired
    UserExerciseRepository uer;

    public UserExercise save(UserExercise userExercise){
        return uer.save(userExercise);
    }

    public UserExercise update(UserExercise userExercise){
        if(userExercise.getId() != null){
            return uer.save(userExercise);
        }else{
            throw new IllegalStateException("No id found.");
        }
    }

    public String delete(Long id) throws UserExerciseIdException {
        if(uer.findById(id).isPresent()) {
            uer.delete(uer.findById(id).get());
            return "Exercise deleted.";
        }else{
            throw new UserExerciseIdException(id);
        }
    }

    public List<UserExercise> getAll(){return uer.findAll();}

    public Page<UserExercise> getAllPaginate(Pageable p) {
        return uer.findAll(p);
    }


    /*
    * Microservice stuff
    * */

    /**
     * Gets a List of IDs as input,
     * returns the corresponding UserExercises
     * */
    public List<UserExercise> getUserExercises(List<Long> userExerciseIds){
        List<UserExercise> res = new ArrayList<>();
        for(Long id : userExerciseIds){
            Optional<UserExercise> ue = uer.findById(id);
            ue.ifPresent(res::add);
        }
        return res;
    }
}

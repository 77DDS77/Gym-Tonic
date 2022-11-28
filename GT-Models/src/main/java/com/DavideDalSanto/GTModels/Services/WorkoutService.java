package com.DavideDalSanto.GTModels.Services;

import com.DavideDalSanto.GTModels.Entities.Plan;
import com.DavideDalSanto.GTModels.Entities.Workout;
import com.DavideDalSanto.GTModels.Exceptions.WorkoutIdException;
import com.DavideDalSanto.GTModels.Repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkoutService {

    @Autowired
    WorkoutRepository wr;

    public Workout save(Workout workout) {
        return wr.save(workout);
    }

    public Workout update(Workout workout){
        if(workout.getId() != null){
            return wr.save(workout);
        }else{
            throw new IllegalStateException("No id found.");
        }
    }

    public String delete(Long id) throws WorkoutIdException {
        if(wr.findById(id).isPresent()) {
            wr.delete(wr.findById(id).get());
            return "Workout deleted.";
        }else{
            throw new WorkoutIdException(id);
        }
    }

    public List<Workout> getAll(){return wr.findAll();}

    public Page<Workout> getAllPaginate(Pageable p) {
        return wr.findAll(p);
    }

    public List<Workout> getUserWorkouts(List<Long> userWorkoutIds){
        List<Workout> res = new ArrayList<>();
        for(Long id : userWorkoutIds){
            Optional<Workout> p = wr.findById(id);
            p.ifPresent(res::add);
        }
        return res;
    }
}

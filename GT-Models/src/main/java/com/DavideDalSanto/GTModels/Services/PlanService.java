package com.DavideDalSanto.GTModels.Services;

import com.DavideDalSanto.GTModels.Entities.Plan;
import com.DavideDalSanto.GTModels.Exceptions.PlanIdException;
import com.DavideDalSanto.GTModels.Repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    PlanRepository pr;

    public Plan save(Plan plan){ return pr.save(plan); }

    public Plan update(Plan plan){
        if(plan.getId() != null){
            return pr.save(plan);
        }else{
            throw new IllegalStateException("No id found.");
        }
    }

    public String delete(Long id) throws PlanIdException {
        if(pr.findById(id).isPresent()) {
            pr.delete(pr.findById(id).get());
            return "Plan deleted.";
        }else{
            throw new PlanIdException(id);
        }
    }

    public List<Plan> getAll(){return pr.findAll();}

    public Page<Plan> getAllPaginate(Pageable p) {
        return pr.findAll(p);
    }

    /*
    * Microservices talking do not disturb.
    * */

    /**
    * Get the GTUser plans from the microservice,
    * cycles and fetches them from the DB.
    * */
    public List<Plan> getUserPlans(List<Long> userPlansIds){
        List<Plan> res = new ArrayList<>();
        for(Long id : userPlansIds){
            Optional<Plan> p = pr.findById(id);
            p.ifPresent(res::add);
        }
        return res;
    }
}

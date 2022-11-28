package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.DTO.PlanDTO;
import com.DavideDalSanto.GTUser.Entities.GTUser;
import com.DavideDalSanto.GTUser.Repositories.GTUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class PlanService {

    @Autowired
    private GTUserRepository ur;

    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Given the GTUser ID find in the models Server his plans
     * and returns them.
     * */
    public List<PlanDTO> getPlan(Long GTUserID) throws IOException, InterruptedException, URISyntaxException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            List<Long> userPlans = found.get().getUserPlansIds();
            String plansIds = objectMapper.writeValueAsString(userPlans);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/plans/user-plans"))
                    .POST(HttpRequest.BodyPublishers.ofString(plansIds))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            List<PlanDTO> res = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructCollectionLikeType(List.class, PlanDTO.class));

            System.out.println("EUREKA");
            return res;
        }
        return null;
    }

    /**
     * Post a new Plan on the Models Server,
     * updates the given GTUser's plans List.
     * */
    public PlanDTO postNewPlan(Long GTUserID, PlanDTO plan) throws IOException, InterruptedException, URISyntaxException {

        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            String stringedPlan = objectMapper.writeValueAsString(plan);

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:9090/GT/plans/new-plan"))
                    .POST(HttpRequest.BodyPublishers.ofString(stringedPlan))
                    .build();

            HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

            PlanDTO newPlan = objectMapper.readValue(
                    postResponse.body(),
                    objectMapper.getTypeFactory().constructType(PlanDTO.class));

            found.get().getUserPlansIds().add(newPlan.getId());
            ur.save(found.get());
            return newPlan;
        }
        return null;
    }

    /**
     * Given the GTUser ID and the Plan ID checks
     * if the user exist and if the given Plan
     * exists in his profile, if both true deletes the
     * Plan and update the GTUser profile
     * */
    public String deletePlan(Long GTUserID, Long planID) throws IOException, URISyntaxException, InterruptedException {
        Optional<GTUser> found = ur.findById(GTUserID);
        if(found.isPresent()){
            if(found.get().getUserPlansIds().contains(planID)){
                String stringedPlan= objectMapper.writeValueAsString(planID);

                HttpClient httpClient = HttpClient.newHttpClient();

                HttpRequest postRequest = HttpRequest.newBuilder()
                        .uri(new URI("http://localhost:9090/GT/plans/delete-workout")) //TODO mettere url
                        .POST(HttpRequest.BodyPublishers.ofString(stringedPlan))
                        .build();

                HttpResponse<String> postResponse= httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

                found.get().getUserPlansIds().remove(planID);
                ur.save(found.get());
                return postResponse.body();
            }
            return "User ("+ GTUserID + ") have no Plan with id (" + planID + ").";
        }
        return "User ("+ GTUserID + ") not found";
    }
}

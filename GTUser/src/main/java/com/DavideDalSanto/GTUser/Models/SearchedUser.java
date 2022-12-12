package com.DavideDalSanto.GTUser.Models;

import lombok.*;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchedUser {
    private Long id;
    private String username;
    private String name;
    private String lastname;

    @Builder.Default
    private List<Long> userExercisesId= new ArrayList<>();
    @Builder.Default
    private List<Long> userWorkoutsId = new ArrayList<>();
    @Builder.Default
    private List<Long> userPlansIds= new ArrayList<>();


}

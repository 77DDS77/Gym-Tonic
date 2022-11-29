package com.DavideDalSanto.GTUser.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gt_pt")
@Getter
@Setter
@SuperBuilder
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GTPersonalTrainer extends JWTUser{

    @Builder.Default
    @ElementCollection
    List<Long> gtUserIds = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    private List<Long> userPlansIds= new ArrayList<>();

    @Builder.Default
    @ElementCollection
    private List<Long> userWorkoutsId = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    private List<Long> userExercisesId= new ArrayList<>();
}

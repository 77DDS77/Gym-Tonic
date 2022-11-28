package com.DavideDalSanto.GTUser.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    @OneToMany
    List<GTUser> clientList;
}

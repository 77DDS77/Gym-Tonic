package com.DavideDalSanto.GTUser.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class JWTUser {


    @Id
    @SequenceGenerator(name = "jwt_users_seq", sequenceName = "jwt_users_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jwt_users_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String name;
    private String lastname;

    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();


    @Builder.Default
    @ElementCollection
    private List<Long> userPlansIds= new ArrayList<>();

    @Builder.Default
    @ElementCollection
    private List<Long> userWorkoutsId = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    private List<Long> userExercisesId= new ArrayList<>();

    public JWTUser(String username, String email, String name, String lastname, String password) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
    }

    public void addRole(Role r){
        this.roles.add(r);
    }

    public void removeRole(Role r){
        this.roles.remove(r);
    }
}

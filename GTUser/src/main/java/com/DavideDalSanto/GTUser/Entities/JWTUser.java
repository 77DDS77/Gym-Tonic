package com.DavideDalSanto.GTUser.Entities;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
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
    private String nome;
    private String cognome;

    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<Role>();

    public JWTUser(String username, String email, String nome, String cognome, String password) {
        this.username = username;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
    }

    public void addRole(Role r){
        this.roles.add(r);
    }

    public void removeRole(Role r){
        this.roles.remove(r);
    }
}

package com.DavideDalSanto.GTUser.Repositories;

import com.DavideDalSanto.GTUser.Entities.JWTUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JWTUserRepository extends JpaRepository<JWTUser, Long> {
    Optional<JWTUser> findByUsername(String username);
}

package com.DavideDalSanto.GTUser.Repositories;

import com.DavideDalSanto.GTUser.Entities.GTUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GTUserRepository extends JpaRepository<GTUser, Long> {
    @Query("select g from GTUser g where upper(g.username) like upper(concat('%', ?1, '%'))")
    List<GTUser> findByUsernameContainsIgnoreCase(String username);
}

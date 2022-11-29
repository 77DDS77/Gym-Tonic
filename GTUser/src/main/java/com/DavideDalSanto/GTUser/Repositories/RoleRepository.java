package com.DavideDalSanto.GTUser.Repositories;

import com.DavideDalSanto.GTUser.Entities.Role;
import com.DavideDalSanto.GTUser.Entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.roleType = ?1")
    Optional<Role> findByRoleType(RoleType roleType);


}

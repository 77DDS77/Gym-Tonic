package com.DavideDalSanto.GTUser.Repositories;

import com.DavideDalSanto.GTUser.Entities.GTPersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GTPTRepository extends JpaRepository<GTPersonalTrainer, Long> {

}

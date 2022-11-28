package com.DavideDalSanto.GTModels.Repositories;

import com.DavideDalSanto.GTModels.Entities.UserExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserExerciseRepository extends JpaRepository<UserExercise, Long> {
    //TODO UserExerciseRepository
}

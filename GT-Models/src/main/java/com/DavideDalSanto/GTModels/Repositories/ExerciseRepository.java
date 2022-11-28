package com.DavideDalSanto.GTModels.Repositories;

import com.DavideDalSanto.GTModels.Entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("select e from Exercise e where e.muscle = ?1")
    List<Exercise> findByMuscle(String muscle);

    @Query("select e from Exercise e where e.name like concat('%', ?1, '%')")
    List<Exercise> findByNameContains(String name);
    @Query("select e from Exercise e where e.name = ?1")
    Optional<Exercise> findByName(String name);
}

package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.exercise.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findById(String name);

    boolean existsById(String name);
}

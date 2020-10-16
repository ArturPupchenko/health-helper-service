package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {

    void deleteByTrainingAndExerciseAndUser(Training training, Exercise exercise, HealthHelperUser user);

    Optional<Result> findByTrainingIdAndExerciseIdAndUserId(long trainingId, long exerciseId, long userId);

    void deleteByTrainingIdAndExerciseIdAndUserId(long trainingId, long exerciseId, long userId);

    boolean existsByTrainingIdAndExerciseIdAndUserId(long trainingId, long exerciseId, long userId);
}


package com.alevel.java.healthhepler.service.exercise;

import com.alevel.java.healthhepler.model.exercise.response.ExerciseResponse;
import com.alevel.java.healthhepler.model.user.response.UserResponse;
import com.alevel.java.healthhepler.repository.ExerciseRepository;
import com.alevel.java.healthhepler.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ExerciseService implements ExerciseOperations {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExerciseResponse> findByName(String name) {
        return exerciseRepository.findByName(name).map(ExerciseResponse::fromExercise);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExerciseResponse> list(Pageable pageable) {
        return exerciseRepository.findAll(pageable).map(ExerciseResponse::fromExercise);
    }
}

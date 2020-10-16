package com.alevel.java.healthhepler.service.exercise;

import com.alevel.java.healthhepler.model.exercise.request.SaveExerciseRequest;
import com.alevel.java.healthhepler.model.exercise.response.ExerciseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ExerciseOperations {

    Optional<ExerciseResponse> findById(Long id);

    Page<ExerciseResponse> list(Pageable pageable);

    ExerciseResponse create(SaveExerciseRequest request);

    @Transactional
    void deleteById(long id);
}

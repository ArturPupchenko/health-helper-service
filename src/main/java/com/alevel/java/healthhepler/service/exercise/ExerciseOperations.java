package com.alevel.java.healthhepler.service.exercise;

import com.alevel.java.healthhepler.model.exercise.response.ExerciseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ExerciseOperations {

    Optional<ExerciseResponse> findByName(String name);

    Page<ExerciseResponse> list(Pageable pageable);



}

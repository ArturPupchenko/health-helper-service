package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.model.exercise.response.ExerciseResponse;
import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import com.alevel.java.healthhepler.model.user.response.UserResponse;
import com.alevel.java.healthhepler.service.exercise.ExerciseOperations;
import com.alevel.java.healthhepler.service.training.TrainingOperations;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseOperations exerciseOperations;

    public ExerciseController(ExerciseOperations exerciseOperations) {
        this.exerciseOperations = exerciseOperations;
    }

    @GetMapping
    @PageableAsQueryParam
    public Page<ExerciseResponse> listExercises(@Parameter(hidden = true) Pageable pageable) {
        return exerciseOperations.list(pageable);
    }
}

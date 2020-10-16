package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.exercise.request.SaveExerciseRequest;
import com.alevel.java.healthhepler.model.exercise.response.ExerciseResponse;
import com.alevel.java.healthhepler.service.exercise.ExerciseOperations;
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
    public Page<ExerciseResponse> listAllExercises(@Parameter(hidden = true) Pageable pageable) {
        return exerciseOperations.list(pageable);
    }

    @GetMapping("/{id}")
    public ExerciseResponse getExerciseById(@PathVariable long id) {
        return exerciseOperations.findById(id).orElseThrow(() -> HealthHelperExceptions.exerciseNotFound(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponse addExercise(@RequestBody @Valid SaveExerciseRequest request) {
        return exerciseOperations.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteExerciseById(@PathVariable long id) {
        exerciseOperations.deleteById(id);
    }

}

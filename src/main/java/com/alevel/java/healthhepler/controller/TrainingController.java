package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import com.alevel.java.healthhepler.service.training.TrainingOperations;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingOperations trainingOperations;


    public TrainingController(TrainingOperations trainingOperations) {
        this.trainingOperations = trainingOperations;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingResponse arrange(@RequestBody @Valid SaveTrainingRequest request, @AuthenticationPrincipal String email) {
        return trainingOperations.create(request, email);
    }

    @GetMapping
    @PageableAsQueryParam
    public Page<TrainingResponse> listTrainings(@Parameter(hidden = true) Pageable pageable, @AuthenticationPrincipal String email) {
        return trainingOperations.list(pageable, email);
    }

    @GetMapping("/{id}")
    public TrainingResponse getTrainingById(@PathVariable long id, @AuthenticationPrincipal String email) {
        return trainingOperations.findById(id, email);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrainingById(@PathVariable long id, @AuthenticationPrincipal String email) {
        trainingOperations.deleteById(id, email);
    }
}

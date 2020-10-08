package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import com.alevel.java.healthhepler.service.training.TrainingOperations;
import org.springframework.http.HttpStatus;
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
    public TrainingResponse arrange(@RequestBody @Valid SaveTrainingRequest request) {
        return trainingOperations.create(request);
    }
}

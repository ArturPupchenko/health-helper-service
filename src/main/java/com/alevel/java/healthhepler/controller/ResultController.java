package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.model.result.request.SaveResultRequest;
import com.alevel.java.healthhepler.model.result.response.ResultResponse;
import com.alevel.java.healthhepler.service.result.ResultOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultOperations resultOperations;

    public ResultController(ResultOperations resultOperations) {
        this.resultOperations = resultOperations;
    }


    @PostMapping("/{trainingId}/{exerciseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse add(@PathVariable long trainingId,@PathVariable long exerciseId,@RequestBody @Valid SaveResultRequest request, @AuthenticationPrincipal String email) {
        return resultOperations.create(trainingId, exerciseId, request, email);
    }

    @GetMapping("/{trainingId}/{exerciseId}")
    public ResultResponse getResult(@PathVariable long trainingId,@PathVariable long exerciseId, @AuthenticationPrincipal String email) {
        return resultOperations.findByTrainingAndExercise(trainingId, exerciseId, email);
    }

    @DeleteMapping("/{trainingId}/{exerciseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResult(@PathVariable long trainingId,@PathVariable long exerciseId, @AuthenticationPrincipal String email) {
        resultOperations.deleteByTrainingAndExercise(trainingId, exerciseId, email);
    }
}

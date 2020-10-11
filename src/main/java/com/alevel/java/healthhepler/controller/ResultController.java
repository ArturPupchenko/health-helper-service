package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.model.result.request.SaveResultRequest;
import com.alevel.java.healthhepler.model.result.response.ResultResponse;
import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResultResponse arrange(@RequestBody @Valid SaveResultRequest request, @AuthenticationPrincipal String email) {
        return resultOperations.create(request, email);
    }
}

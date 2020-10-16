package com.alevel.java.healthhepler.service.result;


import com.alevel.java.healthhepler.model.result.request.SaveResultRequest;
import com.alevel.java.healthhepler.model.result.response.ResultResponse;

public interface ResultOperations {

    ResultResponse create(long trainingId, long exerciseId, SaveResultRequest request, String email);

    ResultResponse findByTrainingAndExercise(long trainingId, long exerciseId, String email);

    void deleteByTrainingAndExercise(long trainingId, long exerciseId, String email);
}

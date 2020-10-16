package com.alevel.java.healthhepler.service.result;


import com.alevel.java.healthhepler.model.result.request.SaveResultRequest;
import com.alevel.java.healthhepler.model.result.response.ResultResponse;

public interface ResultOperations {

    ResultResponse create(SaveResultRequest request, String email);

    ResultResponse findById(long id, String email);

    void deleteById(long id, String email);
}

package com.alevel.java.healthhepler.service.training;

import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TrainingOperations {

    Page<TrainingResponse> list(Pageable pageable);

    TrainingResponse create(SaveTrainingRequest request, String email);

    void deleteById(long id);
}

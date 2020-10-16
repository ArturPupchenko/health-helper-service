package com.alevel.java.healthhepler.service.training;

import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TrainingOperations {

    Page<TrainingResponse> list(Pageable pageable, String email);

    TrainingResponse create(SaveTrainingRequest request, String email);

    TrainingResponse findById(long id, String email);

    void deleteById(long id, String email);
}

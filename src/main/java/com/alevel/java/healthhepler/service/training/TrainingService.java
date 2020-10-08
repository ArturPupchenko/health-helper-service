package com.alevel.java.healthhepler.service.training;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import com.alevel.java.healthhepler.repository.TrainingRepository;
import com.alevel.java.healthhepler.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;

@Service
public class TrainingService implements TrainingOperations {

    private final TrainingRepository trainingRepository;

    private final UserRepository userRepository;

    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public TrainingResponse create(SaveTrainingRequest request) {
        return TrainingResponse.fromTraining(save(request));

    }

    private Training save(SaveTrainingRequest request) {
        var training = new Training();
        training.setUser(userRepository.findById(request.getUserId()).orElseThrow(() -> HealthHelperExceptions.userNotFound(request.getUserId())));
        training.setDate(OffsetDateTime.now());
        trainingRepository.save(training);
        return training;
    }


    @Override
    public Page<TrainingResponse> list(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }
}

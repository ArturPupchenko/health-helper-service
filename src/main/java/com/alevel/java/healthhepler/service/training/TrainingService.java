package com.alevel.java.healthhepler.service.training;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import com.alevel.java.healthhepler.repository.ExerciseRepository;
import com.alevel.java.healthhepler.repository.TrainingRepository;
import com.alevel.java.healthhepler.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class TrainingService implements TrainingOperations {

    private final UserRepository userRepository;

    private final TrainingRepository trainingRepository;

    private final ExerciseRepository exerciseRepository;


    public TrainingService(TrainingRepository trainingRepository, UserRepository userRepository, ExerciseRepository exerciseRepository) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional
    public TrainingResponse create(SaveTrainingRequest request, String email) {
        return TrainingResponse.fromTraining(save(request, email));

    }

    private Training save(SaveTrainingRequest request, String email) {
        var training = new Training();
        training.setUser(userRepository.findByEmailOrNickname(email, email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email)));
        training.setDate(OffsetDateTime.now());
        Set<Exercise> exercises = new HashSet<>();
        for (String name : request.getExerciseNames()) {
            exercises.add(exerciseRepository.findByName(name).orElseThrow());
        }
        training.setExercises(exercises);
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

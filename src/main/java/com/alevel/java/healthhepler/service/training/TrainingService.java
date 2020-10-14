package com.alevel.java.healthhepler.service.training;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.training.request.SaveTrainingRequest;
import com.alevel.java.healthhepler.model.training.response.TrainingResponse;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
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
        validateTrainingFields(request, email);
        return TrainingResponse.fromTraining(save(request, email));

    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainingResponse> list(Pageable pageable) {
        return trainingRepository.findAll(pageable).map(TrainingResponse::fromTraining);
    }

    @Override
    public TrainingResponse findById(long id, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        Training training = trainingRepository.findById(id).orElseThrow(() -> HealthHelperExceptions.trainingNotFound(id));
        if (training.getUser().getId() == user.getId()) return TrainingResponse.fromTraining(training);
        else throw HealthHelperExceptions.trainingNotFound(id);
    }

    @Override
    public void deleteById(long id) {

    }


    private Training save(SaveTrainingRequest request, String email) {
        var training = new Training();
        training.setUser(userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email)));
        training.setDate(request.getDate());
        Set<Exercise> exercises = new HashSet<>();
        for (Long exerciseId : request.getExerciseIds()) {
            exercises.add(exerciseRepository.findById(exerciseId).orElseThrow(() -> HealthHelperExceptions.invalidExercises()));
        }
        training.setExercises(exercises);
        trainingRepository.save(training);
        return training;
    }

    private void validateTrainingFields(SaveTrainingRequest request, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        Long userId = user.getId();
        OffsetDateTime date = request.getDate();
        if (date.isBefore(OffsetDateTime.now())) throw HealthHelperExceptions.invalidTrainingDate(date);
        if (trainingRepository.existsByUserIdAndDate(userId, date))
            throw HealthHelperExceptions.invalidTrainingDate(date);
        Long[] exerciseIds = request.getExerciseIds();
        if (0 == exerciseIds.length) throw HealthHelperExceptions.invalidExercises();
        for (Long exerciseId : exerciseIds) {
            if (!exerciseRepository.existsById(exerciseId)) throw HealthHelperExceptions.invalidExercises();
        }

    }


}

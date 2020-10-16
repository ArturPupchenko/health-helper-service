package com.alevel.java.healthhepler.service.result;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.result.request.SaveResultRequest;
import com.alevel.java.healthhepler.model.result.response.ResultResponse;
import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import com.alevel.java.healthhepler.repository.ExerciseRepository;
import com.alevel.java.healthhepler.repository.ResultRepository;
import com.alevel.java.healthhepler.repository.TrainingRepository;
import com.alevel.java.healthhepler.repository.UserRepository;
import com.alevel.java.healthhepler.service.user.UserOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ResultService implements ResultOperations {

    private final UserRepository userRepository;

    private final TrainingRepository trainingRepository;

    private final ExerciseRepository exerciseRepository;

    private final ResultRepository resultRepository;

    private final UserOperations userOperations;


    public ResultService(UserRepository userRepository,
                         TrainingRepository trainingRepository,
                         ExerciseRepository exerciseRepository,
                         ResultRepository resultRepository, UserOperations userOperations) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.exerciseRepository = exerciseRepository;
        this.resultRepository = resultRepository;
        this.userOperations = userOperations;
    }

    @Override
    @Transactional
    public ResultResponse create(long trainingId, long exerciseId, SaveResultRequest request, String email) {
        return ResultResponse.fromResult(save(trainingId, exerciseId, request, email));
    }

    @Override
    @Transactional(readOnly = true)
    public ResultResponse findByTrainingAndExercise(long trainingId, long exerciseId, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        long userId = user.getId();
        Result result = resultRepository.findByTrainingIdAndExerciseIdAndUserId(trainingId, exerciseId, userId).orElseThrow(HealthHelperExceptions::resultNotFound);
        return ResultResponse.fromResult(result);
    }

    @Override
    @Transactional
    public void deleteByTrainingAndExercise(long trainingId, long exerciseId, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        long userId = user.getId();
        resultRepository.deleteByTrainingIdAndExerciseIdAndUserId(trainingId, exerciseId, userId);
    }

    private Result save(long trainingId, long exerciseId, SaveResultRequest request, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        long userId = user.getId();
        if (resultRepository.existsByTrainingIdAndExerciseIdAndUserId(trainingId, exerciseId, userId))
            throw HealthHelperExceptions.duplicateResult();
        var result = new Result();
        result.setUser(user);
        Training training = trainingRepository.findById(trainingId).orElseThrow(() -> HealthHelperExceptions.trainingNotFound(trainingId));
        result.setTraining(training);
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> HealthHelperExceptions.exerciseNotFound(exerciseId));
        if (!training.getExercises().contains(exercise)) throw HealthHelperExceptions.trainingNotFound(trainingId);
        result.setExercise(exercise);
        result.setWeight(request.getWeight());
        result.setReps(request.getReps());
        resultRepository.save(result);
        return result;
    }
}

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ResultService implements ResultOperations {

    private final UserRepository userRepository;

    private final TrainingRepository trainingRepository;

    private final ExerciseRepository exerciseRepository;

    private final ResultRepository resultRepository;


    public ResultService(UserRepository userRepository,
                         TrainingRepository trainingRepository,
                         ExerciseRepository exerciseRepository,
                         ResultRepository resultRepository) {
        this.userRepository = userRepository;
        this.trainingRepository = trainingRepository;
        this.exerciseRepository = exerciseRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    @Transactional
    public ResultResponse create(SaveResultRequest request, String email) {
        return ResultResponse.fromResult(save(request, email));

    }

    @Override
    public ResultResponse findById(long id, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        Result result = resultRepository.findByIdAndUser(id, user).orElseThrow(() -> HealthHelperExceptions.resultNotFound(id));
        return ResultResponse.fromResult(result);
    }

    @Override
    public void deleteById(long id, String email) {
        HealthHelperUser user = userRepository.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        Result result = resultRepository.findByIdAndUser(id, user).orElseThrow(() -> HealthHelperExceptions.resultNotFound(id));
        resultRepository.deleteById(id);
    }

    private Result save(SaveResultRequest request, String email) {
        var result = new Result();
        HealthHelperUser user = userRepository.findByEmailOrNickname(email, email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        result.setUser(user);
        long trainingId = request.getTrainingId();
        Training training = trainingRepository.findById(trainingId).orElseThrow(() -> HealthHelperExceptions.trainingNotFound(trainingId));
        result.setTraining(training);
        long exerciseId = request.getExerciseId();
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> HealthHelperExceptions.exerciseNotFound(exerciseId));
        if (!training.getExercises().contains(exercise)) throw HealthHelperExceptions.trainingNotFound(trainingId);
        result.setExercise(exercise);
        result.setWeight(request.getWeight());
        result.setReps(request.getReps());
        resultRepository.save(result);
        return result;
    }
}

package com.alevel.java.healthhepler.service.result;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.result.request.SaveResultRequest;
import com.alevel.java.healthhepler.model.result.response.ResultResponse;
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

    private Result save(SaveResultRequest request, String email) {
        var result = new Result();
        result.setUser(userRepository.findByEmailOrNickname(email, email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email)));
        result.setTraining(trainingRepository.findById(request.getTrainingId()).orElseThrow());
        result.setExercise(result.getTraining().getExercises().stream().filter((e) -> e.getId() == request.getExerciseId()).findFirst().orElseThrow());
        result.setWeight(request.getWeight());
        result.setReps(request.getReps());
        resultRepository.save(result);
        return result;
    }
}

package com.alevel.java.healthhepler.service.exercise;

import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.exercise.request.SaveExerciseRequest;
import com.alevel.java.healthhepler.model.exercise.response.ExerciseResponse;
import com.alevel.java.healthhepler.repository.ExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ExerciseService implements ExerciseOperations {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExerciseResponse> findById(Long id) {
        return exerciseRepository.findById(id).map(ExerciseResponse::fromExercise);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExerciseResponse> list(Pageable pageable) {
        return exerciseRepository.findAll(pageable).map(ExerciseResponse::fromExercise);
    }

    @Override
    @Transactional
    public ExerciseResponse create(SaveExerciseRequest request) {
        return ExerciseResponse.fromExercise(save(request));
    }

    private Exercise save(SaveExerciseRequest request) {
        var exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exerciseRepository.save(exercise);
        return exercise;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        exerciseRepository.deleteById(id);
    }


}

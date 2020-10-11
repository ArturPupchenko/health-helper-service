package com.alevel.java.healthhepler.model.result.response;

import com.alevel.java.healthhepler.model.result.Result;

import java.util.Objects;

public class ResultResponse {

    private long id;

    private long trainingId;

    private long exerciseId;

    private long weight;

    private long reps;

    public static ResultResponse fromResult(Result result) {
        var response = new ResultResponse();
        response.id = result.getId();
        response.trainingId = result.getTraining().getId();
        response.exerciseId = result.getExercise().getId();
        response.weight = result.getWeight();
        response.reps = result.getReps();
        return response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(long trainingId) {
        this.trainingId = trainingId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public long getReps() {
        return reps;
    }

    public void setReps(long reps) {
        this.reps = reps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultResponse that = (ResultResponse) o;
        return id == that.id &&
                trainingId == that.trainingId &&
                exerciseId == that.exerciseId &&
                weight == that.weight &&
                reps == that.reps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingId, exerciseId, weight, reps);
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "id=" + id +
                ", trainingId=" + trainingId +
                ", exerciseId=" + exerciseId +
                ", weight=" + weight +
                ", reps=" + reps +
                '}';
    }
}

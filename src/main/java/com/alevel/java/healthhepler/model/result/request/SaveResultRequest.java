package com.alevel.java.healthhepler.model.result.request;


import java.util.Objects;

public class SaveResultRequest {

    private long trainingId;

    private long exerciseId;

    private long weight;

    private long reps;

    public SaveResultRequest(long trainingId, long exerciseId, long weight, long reps) {
        this.trainingId = trainingId;
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.reps = reps;
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
        SaveResultRequest that = (SaveResultRequest) o;
        return trainingId == that.trainingId &&
                exerciseId == that.exerciseId &&
                weight == that.weight &&
                reps == that.reps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, exerciseId, weight, reps);
    }

    @Override
    public String toString() {
        return "SaveResultRequest{" +
                "trainingId=" + trainingId +
                ", exerciseId=" + exerciseId +
                ", weight=" + weight +
                ", reps=" + reps +
                '}';
    }
}

package com.alevel.java.healthhepler.model.result.request;


public class SaveResultRequest {

    private long weight;

    private long reps;

    public SaveResultRequest(long trainingId, long exerciseId, long weight, long reps) {
        this.weight = weight;
        this.reps = reps;
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

}

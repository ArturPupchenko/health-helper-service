package com.alevel.java.healthhepler.model.result;

import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Training training;

    @ManyToOne
    private HealthHelperUser user;

    @ManyToOne
    private Exercise exercise;

    private Long weight;

    private Long reps;

    public Result() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public HealthHelperUser getUser() {
        return user;
    }

    public void setUser(HealthHelperUser user) {
        this.user = user;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getReps() {
        return reps;
    }

    public void setReps(Long reps) {
        this.reps = reps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(id, result.id) &&
                Objects.equals(training, result.training) &&
                Objects.equals(user, result.user) &&
                Objects.equals(exercise, result.exercise) &&
                Objects.equals(weight, result.weight) &&
                Objects.equals(reps, result.reps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, training, user, exercise, weight, reps);
    }
}

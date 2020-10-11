package com.alevel.java.healthhepler.model.training.response;

import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.training.Training;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

public class TrainingResponse {

    private long id;

    private long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime date;

    private Set<Exercise> exercises;

    public static TrainingResponse fromTraining(Training training) {
        var response = new TrainingResponse();
        response.id = training.getId();
        response.userId = training.getUser().getId();
        response.date = training.getDate();
        response.exercises = training.getExercises();
        return response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingResponse that = (TrainingResponse) o;
        return id == that.id &&
                userId == that.userId &&
                Objects.equals(date, that.date) &&
                Objects.equals(exercises, that.exercises);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, date, exercises);
    }
}

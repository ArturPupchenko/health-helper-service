package com.alevel.java.healthhepler.model.exercise.response;


import com.alevel.java.healthhepler.model.exercise.Exercise;

import java.util.Objects;

public class ExerciseResponse {

    private Long id;
    private String name;
    private String description;

    public ExerciseResponse() {
    }

    public ExerciseResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static ExerciseResponse fromExercise(Exercise exercise) {
        var response = new ExerciseResponse();
        response.id = exercise.getId();
        response.name = exercise.getName();
        response.description = exercise.getDescription();
        return response;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseResponse that = (ExerciseResponse) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "ExerciseResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

package com.alevel.java.healthhepler.model.training.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Objects;

public class SaveTrainingRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSx")
    private OffsetDateTime date;

    @NotNull
    private String[] exerciseNames;


    public SaveTrainingRequest(@NotNull OffsetDateTime date, String[] exerciseNames) {
        this.date = date;
        this.exerciseNames = exerciseNames;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public String[] getExerciseNames() {
        return exerciseNames;
    }

    public void setExerciseNames(String[] exerciseNames) {
        this.exerciseNames = exerciseNames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveTrainingRequest that = (SaveTrainingRequest) o;
        return Objects.equals(date, that.date) &&
                Arrays.equals(exerciseNames, that.exerciseNames);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(date);
        result = 31 * result + Arrays.hashCode(exerciseNames);
        return result;
    }
}

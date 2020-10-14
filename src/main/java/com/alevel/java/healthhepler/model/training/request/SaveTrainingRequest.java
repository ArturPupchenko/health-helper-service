package com.alevel.java.healthhepler.model.training.request;


import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

public class SaveTrainingRequest {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSSx")
    private OffsetDateTime date;

    @NotNull
    private Long[] exerciseIds;

    public SaveTrainingRequest(@NotNull OffsetDateTime date, Long[] exerciseIds) {
        this.date = date;
        this.exerciseIds = exerciseIds;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Long[] getExerciseIds() {
        return exerciseIds;
    }

    public void setExerciseIds(Long[] exerciseIds) {
        this.exerciseIds = exerciseIds;
    }
}

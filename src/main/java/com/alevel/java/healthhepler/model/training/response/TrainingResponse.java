package com.alevel.java.healthhepler.model.training.response;

import com.alevel.java.healthhepler.model.training.Training;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TrainingResponse {

    private long id;

    private long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime date;

    public static TrainingResponse fromTraining(Training training) {
        var response = new TrainingResponse();
        response.id = training.getId();
        response.userId = training.getUser().getId();
        response.date = training.getDate();
        return response;
    }
}

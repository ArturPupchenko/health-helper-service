package com.alevel.java.healthhepler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;

public final class HealthHelperExceptions {

    private HealthHelperExceptions() {
    }

    public static ResponseStatusException authorityNotFound(String value) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User authority " + value + " not defined");
    }

    public static ResponseStatusException userNotFound(String email) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found");
    }

    public static ResponseStatusException userNotFound(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
    }


    public static ResponseStatusException duplicateEmail(String email) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email " + email + " already taken");
    }

    public static ResponseStatusException duplicateNickname(String nickname) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname " + nickname + " already taken");
    }

    public static ResponseStatusException exerciseNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exercise with id " + id + " not found");
    }

    public static ResponseStatusException invalidTrainingDate(OffsetDateTime date) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid training date " + date);
    }

    public static ResponseStatusException invalidExercises() {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid exercises");
    }

    public static ResponseStatusException trainingNotFound(long id) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Training with id " + id + " not found");
    }

}

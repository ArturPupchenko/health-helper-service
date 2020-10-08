package com.alevel.java.healthhepler.model.training.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotBlank;
import java.time.ZonedDateTime;

public class SaveTrainingRequest {

    //    @NotBlank(message = "user id must not be blank")
    private long userId;

    //    @NotBlank(message = "date must not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ZonedDateTime date;

    public SaveTrainingRequest() {
    }

    public SaveTrainingRequest(@AuthenticationPrincipal String email, @NotBlank(message = "date must not be blank") ZonedDateTime date) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getPrincipal().toString(); //get logged in username

//        userOperations.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email))
        this.userId = userId;
        this.date = date;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }
}

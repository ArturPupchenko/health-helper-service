package com.alevel.java.healthhepler.model.user.request;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Objects;

public class UserLoginRequest {

    @JsonAlias({"username", "email"})
    private String login;

    private String password;

    public UserLoginRequest() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

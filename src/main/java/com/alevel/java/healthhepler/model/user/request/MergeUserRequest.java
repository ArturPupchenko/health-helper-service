package com.alevel.java.healthhepler.model.user.request;

import com.alevel.java.healthhepler.model.constraints.NullableNotBlank;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Objects;

public class MergeUserRequest {

    @Email(message = "email must be a valid email string")
    private String email;

    @Size(min = 3, message = "password's length must be at least 3")
    private String password;

    @NullableNotBlank(message = "nickname must not be blank")
    private String nickname;

    public MergeUserRequest() {
    }

    public MergeUserRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergeUserRequest that = (MergeUserRequest) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, nickname, password);
    }

    @Override
    public String toString() {
        return "MergeUserRequest{" +
                "email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

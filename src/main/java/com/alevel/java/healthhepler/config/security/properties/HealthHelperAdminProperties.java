package com.alevel.java.healthhepler.config.security.properties;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class HealthHelperAdminProperties {

    @Email(message = "email must be a valid email string")
    @NotNull(message = "email must not be null")
    private String email;

    @NotBlank(message = "password must not be blank")
    @Size(min = 3, message = "password's length must be at least 3")
    private String password;

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
}

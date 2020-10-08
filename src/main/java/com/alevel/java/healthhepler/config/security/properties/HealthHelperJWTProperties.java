package com.alevel.java.healthhepler.config.security.properties;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class HealthHelperJWTProperties {

    @NotBlank
    private String secret;

    @Min(value = 60_000)
    @Max(value = 3_600_000)
    private long expireIn;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }
}

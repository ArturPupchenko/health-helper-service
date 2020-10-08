package com.alevel.java.healthhepler.config.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Validated
@ConfigurationProperties(prefix = "health-helper.security")
public class HealthHelperSecurityProperties {

    @Valid
    @NestedConfigurationProperty
    private HealthHelperJWTProperties jwt;

    private Map<@NotBlank String, @Valid HealthHelperAdminProperties> admins;

    public HealthHelperJWTProperties getJwt() {
        return jwt;
    }

    public void setJwt(HealthHelperJWTProperties jwt) {
        this.jwt = jwt;
    }

    public Map<String, HealthHelperAdminProperties> getAdmins() {
        return admins;
    }

    public void setAdmins(Map<String, HealthHelperAdminProperties> admins) {
        this.admins = admins;
    }
}

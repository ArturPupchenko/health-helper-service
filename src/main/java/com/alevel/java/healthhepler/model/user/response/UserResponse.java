package com.alevel.java.healthhepler.model.user.response;

import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import com.alevel.java.healthhepler.model.user.KnownAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.EnumSet;
import java.util.Set;

public class UserResponse {

    private long id;

    private String email;

    private String nickname;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime createdAt;

    private Set<KnownAuthority> authorities;

    public static UserResponse fromUser(HealthHelperUser user) {
        var response = fromUserWithBasicAttributes(user);
        response.authorities = EnumSet.copyOf(user.getAuthorities().keySet());
        return response;
    }

    // only the attributes that don't require extra fetching
    public static UserResponse fromUserWithBasicAttributes(HealthHelperUser user) {
        var response = new UserResponse();
        response.id = user.getId();
        response.email = user.getEmail();
        response.nickname = user.getNickname();
        response.createdAt = user.getCreatedAt();
        return response;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<KnownAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<KnownAuthority> authorities) {
        this.authorities = authorities;
    }

//    @Override
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    public String getPath() {
//        return Routes.user(id);
//    }
}

package com.alevel.java.healthhepler.model.user;

import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.group.Group;
import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.training.Training;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Table(name = "users")
@SuppressWarnings("FieldMayBeFinal")
public class HealthHelperUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Training> trainings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Result> results = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Group> adminGroups = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id")
    )
    @MapKeyEnumerated(EnumType.STRING)
    @MapKey(name = "value")
    private Map<KnownAuthority, HealthHelperUserAuthority> authorities = new EnumMap<>(KnownAuthority.class);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getAdminGroups() {
        return adminGroups;
    }

    public void setAdminGroups(List<Group> adminGroups) {
        this.adminGroups = adminGroups;
    }

    public Map<KnownAuthority, HealthHelperUserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Map<KnownAuthority, HealthHelperUserAuthority> authorities) {
        this.authorities = authorities;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HealthHelperUser that = (HealthHelperUser) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(email, that.email) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(password, that.password) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(trainings, that.trainings) &&
                Objects.equals(results, that.results) &&
                Objects.equals(groups, that.groups) &&
                Objects.equals(adminGroups, that.adminGroups) &&
                Objects.equals(authorities, that.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, nickname, password, createdAt, trainings, results, groups, adminGroups, authorities);
    }

    @Override
    public String toString() {
        return "HealthHelperUser{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createdAt=" + createdAt +
                ", authorities=" + authorities +
                '}';
    }
}

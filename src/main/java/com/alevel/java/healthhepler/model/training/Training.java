package com.alevel.java.healthhepler.model.training;


import com.alevel.java.healthhepler.model.exercise.Exercise;
import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OffsetDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private HealthHelperUser user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "training_exercises",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "exercise_id"))
    private Set<Exercise> exercises = new HashSet<>();

    @OneToMany(mappedBy = "training")
    private Set<Result> results = new HashSet<>();

    public Training() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public HealthHelperUser getUser() {
        return user;
    }

    public void setUser(HealthHelperUser user) {
        this.user = user;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Set<Result> getResults() {
        return results;
    }

    public void setResults(Set<Result> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Training training = (Training) o;
        return Objects.equals(id, training.id) &&
                Objects.equals(date, training.date) &&
                Objects.equals(user, training.user) &&
                Objects.equals(exercises, training.exercises) &&
                Objects.equals(results, training.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, user, exercises, results);
    }
}

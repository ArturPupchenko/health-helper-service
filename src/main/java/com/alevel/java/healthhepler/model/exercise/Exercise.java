package com.alevel.java.healthhepler.model.exercise;

import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.training.Training;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL,
    orphanRemoval = true)
    private final List<Result> results = new ArrayList<>();

    @ManyToMany(mappedBy = "exercises")
    private final List<Training> trainings = new ArrayList<>();

    public Exercise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

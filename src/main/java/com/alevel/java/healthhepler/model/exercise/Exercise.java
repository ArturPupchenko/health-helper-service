package com.alevel.java.healthhepler.model.exercise;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId(mutable = true)
    @Column(nullable = false, unique = true)
    private String name;
}

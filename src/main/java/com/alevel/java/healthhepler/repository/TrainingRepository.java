package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}

package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.training.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    boolean existsByUserIdAndDate(Long userId, OffsetDateTime date);

    void deleteById(Long id);
}

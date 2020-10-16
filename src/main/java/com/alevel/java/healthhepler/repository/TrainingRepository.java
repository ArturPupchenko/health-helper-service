package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.training.Training;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    Page<Training> findAllByUser(HealthHelperUser user, Pageable pageable);

    boolean existsByUserIdAndDate(Long userId, OffsetDateTime date);

    void deleteById(Long id);

}

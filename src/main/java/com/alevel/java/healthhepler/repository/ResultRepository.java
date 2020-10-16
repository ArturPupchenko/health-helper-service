package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.result.Result;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Long> {
    Optional<Result> findByIdAndUser(long id, HealthHelperUser user);
}

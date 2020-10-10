package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.result.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}

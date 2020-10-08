package com.alevel.java.healthhepler.repository;


import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserRepository extends JpaRepository<HealthHelperUser, Long> {

    Optional<HealthHelperUser> findByEmail(String email);

    Optional<HealthHelperUser> findByEmailOrNickname(String email, String nickname);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    void deleteByEmail(String email);

}

package com.alevel.java.healthhepler.repository;

import com.alevel.java.healthhepler.model.user.HealthHelperUserAuthority;
import com.alevel.java.healthhepler.model.user.KnownAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface AuthorityRepository extends JpaRepository<HealthHelperUserAuthority, Integer> {

    Set<KnownAuthority> ADMIN_AUTHORITIES = EnumSet.of(KnownAuthority.ROLE_USER, KnownAuthority.ROLE_ADMIN);

    Optional<HealthHelperUserAuthority> findByValue(KnownAuthority value);

    Stream<HealthHelperUserAuthority> findAllByValueIn(Set<KnownAuthority> values);

}

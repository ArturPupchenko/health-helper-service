package com.alevel.java.healthhepler.service.user;

import com.alevel.java.healthhepler.model.user.request.MergeUserRequest;
import com.alevel.java.healthhepler.model.user.request.SaveUserRequest;
import com.alevel.java.healthhepler.model.user.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserOperations {

    Page<UserResponse> list(Pageable pageable);

    Optional<UserResponse> findById(long id);

    Optional<UserResponse> findByEmail(String email);

    UserResponse mergeById(long id, MergeUserRequest request);

    UserResponse mergeByEmail(String email, MergeUserRequest request);

    UserResponse create(SaveUserRequest request);

    UserResponse createAdmin(SaveUserRequest request);

    void deleteById(long id);

    void deleteByEmail(String email);
}

package com.alevel.java.healthhepler.controller;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.user.request.MergeUserRequest;
import com.alevel.java.healthhepler.model.user.request.SaveUserRequest;
import com.alevel.java.healthhepler.model.user.response.UserResponse;
import com.alevel.java.healthhepler.service.user.UserOperations;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserOperations userOperations;


    public UserController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    //region user registration

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody @Valid SaveUserRequest request) {
        return userOperations.create(request);
    }

    //endregion

    //region authenticated user API

    @GetMapping("/me")
    public UserResponse getCurrentUser(@AuthenticationPrincipal String email) {
        return userOperations.findByEmail(email).orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
    }

    @PatchMapping("/me")
    public UserResponse mergeCurrentUser(@AuthenticationPrincipal String email,
                                         @RequestBody @Valid MergeUserRequest request) {
        return userOperations.mergeByEmail(email, request);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser(@AuthenticationPrincipal String email) {
        userOperations.deleteByEmail(email);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable long id) {
        return userOperations.findById(id).orElseThrow(() -> HealthHelperExceptions.userNotFound(id));
    }

    @GetMapping
    @PageableAsQueryParam
    public Page<UserResponse> listUsers(@Parameter(hidden = true) Pageable pageable) {
        return userOperations.list(pageable);
    }

    //endregion

    //region admin-only API

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid SaveUserRequest request) {
        return userOperations.createAdmin(request);
    }

    @PatchMapping("/{id}")
    public UserResponse mergeUserById(@PathVariable long id,
                                      @RequestBody @Valid MergeUserRequest request) {
        return userOperations.mergeById(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable long id) {
        userOperations.deleteById(id);
    }

    //endregion

}

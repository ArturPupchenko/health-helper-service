package com.alevel.java.healthhepler.service.user;

import com.alevel.java.healthhepler.exceptions.HealthHelperExceptions;
import com.alevel.java.healthhepler.model.user.HealthHelperUser;
import com.alevel.java.healthhepler.model.user.HealthHelperUserAuthority;
import com.alevel.java.healthhepler.model.user.KnownAuthority;
import com.alevel.java.healthhepler.model.user.request.MergeUserRequest;
import com.alevel.java.healthhepler.model.user.request.SaveUserRequest;
import com.alevel.java.healthhepler.model.user.response.UserResponse;
import com.alevel.java.healthhepler.repository.AuthorityRepository;
import com.alevel.java.healthhepler.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, UserOperations {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            AuthorityRepository authorityRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HealthHelperUser user = userRepository.findByEmailOrNickname(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        Set<KnownAuthority> authorities = EnumSet.copyOf(user.getAuthorities().keySet());

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> list(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserResponse::fromUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findById(long id) {
        return userRepository.findById(id).map(UserResponse::fromUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserResponse::fromUser);
    }

    @Override
    @Transactional
    public UserResponse mergeById(long id, MergeUserRequest request) {
        HealthHelperUser user = getUser(id);
        return UserResponse.fromUser(merge(user, request));
    }

    @Override
    @Transactional
    public UserResponse mergeByEmail(String email, MergeUserRequest request) {
        HealthHelperUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> HealthHelperExceptions.userNotFound(email));
        return UserResponse.fromUser(merge(user, request));
    }

    @Override
    @Transactional
    public UserResponse create(SaveUserRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getRegularUserAuthorities()));
    }

    @Override
    @Transactional
    public UserResponse createAdmin(SaveUserRequest request) {
        validateUniqueFields(request);
        return UserResponse.fromUser(save(request, getAdminAuthorities()));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Transactional
    public void mergeAdmins(List<SaveUserRequest> requests) {
        if (requests.isEmpty()) return;
        Map<KnownAuthority, HealthHelperUserAuthority> authorities = getAdminAuthorities();
        var now = Instant.now();
        for (SaveUserRequest request : requests) {
            String email = request.getEmail();
            String nickname = request.getNickname();
            HealthHelperUser user = userRepository.findByEmail(email).orElseGet(() -> {
                var newUser = new HealthHelperUser();
                newUser.setCreatedAt(OffsetDateTime.now());
                newUser.setEmail(email);
                return newUser;
            });
            if (!nickname.equals(user.getNickname())) {
                if (userRepository.existsByNickname(nickname)) throw HealthHelperExceptions.duplicateNickname(nickname);
                user.setNickname(nickname);
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.getAuthorities().putAll(authorities);
            userRepository.save(user);
        }
    }

    private HealthHelperUser getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> HealthHelperExceptions.userNotFound(id));
    }

    private HealthHelperUser merge(HealthHelperUser user, MergeUserRequest request) {
        String email = request.getEmail();
        if (email != null && !email.equals(user.getEmail())) {
            if (userRepository.existsByEmail(email)) throw HealthHelperExceptions.duplicateEmail(email);
            user.setEmail(email);
        }
        String nickname = request.getNickname();
        if (nickname != null && !nickname.equals(user.getNickname())) {
            if (userRepository.existsByNickname(nickname)) throw HealthHelperExceptions.duplicateNickname(nickname);
            user.setNickname(nickname);
        }
        String password = request.getPassword();
        if (password != null) {
            user.setPassword(passwordEncoder.encode(password));
        }
        return userRepository.save(user);
    }

    private HealthHelperUser save(SaveUserRequest request, Map<KnownAuthority, HealthHelperUserAuthority> authorities) {
        var user = new HealthHelperUser();
        user.getAuthorities().putAll(authorities);
        user.setEmail(request.getEmail());
        user.setNickname(request.getNickname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(OffsetDateTime.now());
        userRepository.save(user);
        return user;
    }

    private Map<KnownAuthority, HealthHelperUserAuthority> getAdminAuthorities() {
        return authorityRepository.findAllByValueIn(AuthorityRepository.ADMIN_AUTHORITIES)
                .collect(Collectors.toMap(
                        HealthHelperUserAuthority::getValue,
                        Function.identity(),
                        (e1, e2) -> e2,
                        () -> new EnumMap<>(KnownAuthority.class)));
    }

    private Map<KnownAuthority, HealthHelperUserAuthority> getRegularUserAuthorities() {
        HealthHelperUserAuthority authority = authorityRepository
                .findByValue(KnownAuthority.ROLE_USER)
                .orElseThrow(() -> HealthHelperExceptions.authorityNotFound(KnownAuthority.ROLE_USER.name()));
        Map<KnownAuthority, HealthHelperUserAuthority> authorities = new EnumMap<>(KnownAuthority.class);
        authorities.put(KnownAuthority.ROLE_USER, authority);
        return authorities;
    }

    private void validateUniqueFields(SaveUserRequest request) {
        String email = request.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw HealthHelperExceptions.duplicateEmail(email);
        }
        String nickname = request.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            throw HealthHelperExceptions.duplicateNickname(nickname);
        }
    }
}

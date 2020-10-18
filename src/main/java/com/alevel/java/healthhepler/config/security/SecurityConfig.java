package com.alevel.java.healthhepler.config.security;

import com.alevel.java.healthhepler.config.security.filters.JWTAuthenticationFilter;
import com.alevel.java.healthhepler.config.security.filters.JWTAuthorizationFilter;
import com.alevel.java.healthhepler.config.security.properties.HealthHelperSecurityProperties;
import com.alevel.java.healthhepler.model.user.request.SaveUserRequest;
import com.alevel.java.healthhepler.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(HealthHelperSecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    private final HealthHelperSecurityProperties securityProperties;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    public SecurityConfig(
            HealthHelperSecurityProperties securityProperties,
            UserService userService,
            PasswordEncoder passwordEncoder,
            ObjectMapper objectMapper
    ) {
        this.securityProperties = securityProperties;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        setupDefaultAdmins();
    }

    private void setupDefaultAdmins() {
        List<SaveUserRequest> requests = securityProperties.getAdmins().entrySet().stream()
                .map(entry -> new SaveUserRequest(
                        entry.getValue().getEmail(),
                        entry.getValue().getPassword(),
                        entry.getKey()))
                .peek(admin -> log.info("Default admin found: {} <{}>", admin.getNickname(), admin.getEmail()))
                .collect(Collectors.toList());
        userService.mergeAdmins(requests);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // open static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // open swagger-ui
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                //allow index.html
                .antMatchers(HttpMethod.GET, "/").permitAll()
                // allow user registration
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                // admin can register new admins
                .antMatchers(HttpMethod.POST, "/users/admins").hasRole("ADMIN")
                // admin can add new exercise
                .antMatchers(HttpMethod.POST, "/exercises").hasRole("ADMIN")
                // admin can delete exercise by id
                .antMatchers(HttpMethod.DELETE, "/exercises/{id:\\d+}/**").hasRole("ADMIN")
                // regular users can view basic user info for other users
                .antMatchers(HttpMethod.GET, "/users/{id:\\d+}").authenticated()
                // admin can manage users by id
                .antMatchers("/users/{id:\\d+}/**").hasRole("ADMIN")
                // admin can use Actuator endpoints
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
                // by default, require authentication
                .anyRequest().authenticated()
                .and()
                // login filter
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), securityProperties.getJwt(), objectMapper))
                // jwt-verification filter
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), securityProperties.getJwt()))
                // for unauthorized requests return 401
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                // allow cross-origin requests for all endpoints
                .cors().configurationSource(corsConfigurationSource())
                .and()
                // we don't need CSRF protection when we use JWT
                // (if you can steal Authorization header value, your can steal X-CSRF as well)
                .csrf().disable()
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    private CorsConfigurationSource corsConfigurationSource() {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

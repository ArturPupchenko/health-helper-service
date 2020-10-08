package com.alevel.java.healthhepler.config.security.filters;

import com.alevel.java.healthhepler.config.security.SecurityConstants;
import com.alevel.java.healthhepler.config.security.properties.HealthHelperJWTProperties;
import com.alevel.java.healthhepler.model.user.KnownAuthority;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    private final HealthHelperJWTProperties jwtProperties;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  HealthHelperJWTProperties jwtProperties) {
        super(authenticationManager);
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(SecurityConstants.AUTH_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        var authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null) return null;

        String encodedJwt = token.substring(SecurityConstants.AUTH_TOKEN_PREFIX.length());

        // parse the token.
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JWT.require(Algorithm.HMAC512(jwtProperties.getSecret().getBytes()))
                    .build()
                    .verify(encodedJwt);
        } catch (JWTVerificationException e) {
            log.debug("Invalid JWT received", e);
            return null;
        }

        String email = decodedJWT.getSubject();

        Set<KnownAuthority> authorities = decodedJWT.getClaim(SecurityConstants.AUTHORITIES_CLAIM)
                .asList(String.class).stream()
                .map(KnownAuthority::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(KnownAuthority.class)));

        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }
}

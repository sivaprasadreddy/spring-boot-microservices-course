package com.sivalabs.bookstore.orders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.StringUtils;

public class MockOAuth2UserContextFactory implements WithSecurityContextFactory<WithMockOAuth2User> {

    public SecurityContext createSecurityContext(WithMockOAuth2User withUser) {
        String username = StringUtils.hasLength(withUser.username()) ? withUser.username() : withUser.value();
        if (username == null) {
            throw new IllegalArgumentException(
                    withUser + " cannot have null username on both username and value properties");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : withUser.roles()) {
            if (role.startsWith("ROLE_")) {
                throw new IllegalArgumentException("roles cannot start with ROLE_ Got " + role);
            }
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }

        Map<String, Object> claims =
                Map.of("preferred_username", username, "userId", withUser.id(), "realm_access", authorities);
        Map<String, Object> headers = Map.of("header", "mock");
        Jwt jwt = new Jwt("mock-jwt-token", Instant.now(), Instant.now().plusSeconds(300), headers, claims);
        Authentication authentication = new JwtAuthenticationToken(jwt, authorities);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}

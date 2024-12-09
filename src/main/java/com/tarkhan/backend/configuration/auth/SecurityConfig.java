package com.tarkhan.backend.configuration.auth;

import com.tarkhan.backend.service.auth.filter.JwtAuthenticationFilter;
import com.tarkhan.backend.service.auth.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserDetailsServiceImpl userDetailsServiceImpl
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/api/v2/auths/register", "/api/v2/auths/login",
                                "/api/v2/authors/**", "/api/v2/books/**",
                                "api/v2/categories/**", "/api/v2/publishers/**",
                                "/api/v2/quotes/**", "/api/v2/blogs/**",
                                "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**",
                                "/api/v2/blogs/**", "/api/v2/tags/**"
                        ).permitAll()
                        .requestMatchers(
                                 "/api/v2/users/**", "/api/v2/readBooks/**",
                                "/api/v2/toReadBooks**", "/api/v2/comments/**",
                                "/api/v2/blogs/user"
                        ).hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/api/v2/authors/admin", "/api/v2/books/admin",
                                "api/v2/categories/admin", "/api/v2/publishers/admin",
                                "/api/v2/quotes/admin", "api/v2/emails/**",
                                "/api/v2/auths/admin/**", "/api/v2/users/admin/**")
                        .hasAnyAuthority("ADMIN")
                        .anyRequest()
                        .authenticated()
                ).userDetailsService(userDetailsServiceImpl)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

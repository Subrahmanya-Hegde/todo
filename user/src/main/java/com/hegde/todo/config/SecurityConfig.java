package com.hegde.todo.config;

import com.hegde.todo.filter.JwtAuthenticationFilter;
import com.hegde.todo.service.UserAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)//This is the custom exception class for all authentication related exceptions
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(userAuthProvider), BasicAuthenticationFilter.class)//JwtFilter before any auth filter
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Stateless application. No cookie set by spring
                .and()
                .authorizeHttpRequests((requests) ->  requests
                        .requestMatchers(HttpMethod.POST, "v0/auth/**").permitAll()//No Authentication for these APIs
                        .anyRequest().authenticated()
                ).build();
    }
}

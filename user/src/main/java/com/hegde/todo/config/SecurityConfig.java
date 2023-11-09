package com.hegde.todo.config;

import com.hegde.todo.filter.JwtAuthenticationFilter;
import com.hegde.todo.service.CustomUserDetailService;
import com.hegde.todo.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final CustomUserDetailService userDetailService;
    private final JwtService userAuthProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)//This is the custom exception class for all authentication related exceptions
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(userAuthProvider, userDetailService), UsernamePasswordAuthenticationFilter.class)//JwtFilter before any auth filter
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//Stateless application. No cookie set by spring
                .and()
                .authorizeHttpRequests((requests) ->  requests
                        .requestMatchers(HttpMethod.POST, "v0/auth/register", "v0/auth/login").permitAll()//No Authentication for these APIs
                        .anyRequest().authenticated()
                ).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

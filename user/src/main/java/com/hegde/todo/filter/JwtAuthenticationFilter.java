package com.hegde.todo.filter;

import com.hegde.todo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("In Filter");
        final String authHeader = request.getHeader("Authorization");
        if(Objects.isNull(authHeader) || authHeader.startsWith("bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        final String jwtToken = authHeader.substring(7);
        final String userName = jwtService.extractUserName(jwtToken);
        
        filterChain.doFilter(request, response);
    }
}

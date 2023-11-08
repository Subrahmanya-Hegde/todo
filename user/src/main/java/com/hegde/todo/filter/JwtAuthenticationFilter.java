package com.hegde.todo.filter;

import com.hegde.todo.service.JwtService;
import com.hegde.todo.service.UserAuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserAuthProvider userAuthProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader != null){
            String[] elements = authHeader.split(" ");
            if(elements.length == 2 && "Bearer".equals(elements[0])){
                try{
                    SecurityContextHolder.getContext().setAuthentication(
                        userAuthProvider.validateToken(elements[1])//This allows us to access UserPrincipal in all controllers
                    );
                }catch (RuntimeException exception){
                    SecurityContextHolder.clearContext();
                    throw exception;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

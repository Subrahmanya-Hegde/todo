package com.hegde.todo.filter;

import com.hegde.todo.service.CustomUserDetailService;
import com.hegde.todo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> tokenOptional = getJwtTokenFromRequest(request);
        if (tokenOptional.isPresent()) {
            String token = tokenOptional.get();
            if (jwtService.validateToken(token)) {
                Optional<UserDetails> userDetailsOptional = getUserDetails(token);
                if (userDetailsOptional.isPresent() && jwtService.validateUserDetails(token, userDetailsOptional.get())){
                    //TODO : Check more on the commented line
                    //&& Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
                    setAuthentication(request, userDetailsOptional.get());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<UserDetails> getUserDetails(String token) {
        String email = jwtService.extractUserEmail(token);
        if (Objects.isNull(email)) {
            return Optional.empty();
        }
        return Optional.of(userDetailService.loadUserByUsername(email));
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private Optional<String> getJwtTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(7));
    }
}

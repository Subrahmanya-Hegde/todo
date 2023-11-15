package com.hegde.todo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;


@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {

    //TODO : Log the necessary details.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Log request
        logRequest(request);

        // Wrap the response to capture the content
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(request, responseWrapper);
        } finally {
            logResponse(responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(HttpServletRequest request) {
        log.info("Server received request  {} {}",request.getMethod(), request.getRequestURI());
    }

    private void logResponse(HttpServletResponse response) {
        log.info("Server responded with Response Code : " + response.getStatus());
    }
}

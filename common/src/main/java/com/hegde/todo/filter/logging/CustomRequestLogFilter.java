package com.hegde.todo.filter.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomRequestLogFilter extends AbstractRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isDebugEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {

    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        this.logger.debug(message);
    }

    @Override
    protected void afterResponse(HttpServletResponse response, String message) {
        this.logger.debug(message);
    }
}

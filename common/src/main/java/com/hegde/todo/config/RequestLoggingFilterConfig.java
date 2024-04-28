package com.hegde.todo.config;

import com.hegde.todo.filter.logging.CustomRequestLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
public class RequestLoggingFilterConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CustomRequestLogFilter logFilter() {
        CustomRequestLogFilter filter = new CustomRequestLogFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        filter.setIncludeClientInfo(true);
        filter.setIncludeResponse(true);
        //TODO : Enable body
        filter.setIncludeResponseBody(false);
        filter.setIncludeResponseHeaders(true);
        return filter;
    }
}

package com.hegde.todo.filter.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractRequestLoggingFilter extends OncePerRequestFilter {

    /**
     * The default value prepended to the log message written <i>before</i> a request is
     * processed.
     */
    public static final String DEFAULT_BEFORE_MESSAGE_PREFIX = "Server Received Request : ";

    /**
     * The default value appended to the log message written <i>before</i> a request is
     * processed.
     */
    public static final String DEFAULT_BEFORE_MESSAGE_SUFFIX = "";

    /**
     * The default value prepended to the log message written <i>after</i> a request is
     * processed.
     */
    public static final String DEFAULT_AFTER_MESSAGE_PREFIX = "Request Received: ";

    /**
     * The default value appended to the log message written <i>after</i> a request is
     * processed.
     */
    public static final String DEFAULT_AFTER_MESSAGE_SUFFIX = "";

    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 50;

    private static final String NEW_LINE = "\n";


    private boolean includeQueryString = false;

    private boolean includeClientInfo = false;

    private boolean includeHeaders = false;

    private boolean includePayload = false;

    public boolean isIncludeResponse() {
        return includeResponse;
    }

    public void setIncludeResponse(boolean includeResponse) {
        this.includeResponse = includeResponse;
    }

    public boolean isIncludeResponseBody() {
        return isIncludeResponseBody;
    }

    public void setIncludeResponseBody(boolean includeResponseBody) {
        isIncludeResponseBody = includeResponseBody;
    }

    public boolean isIncludeResponseHeaders() {
        return isIncludeResponseHeaders;
    }

    public void setIncludeResponseHeaders(boolean includeResponseHeaders) {
        isIncludeResponseHeaders = includeResponseHeaders;
    }

    private boolean includeResponse = false;
    private boolean isIncludeResponseBody = false;
    private boolean isIncludeResponseHeaders = false;

    @Nullable
    private Predicate<String> headerPredicate;

    private int maxPayloadLength = DEFAULT_MAX_PAYLOAD_LENGTH;

    private String beforeMessagePrefix = DEFAULT_BEFORE_MESSAGE_PREFIX;

    private String beforeMessageSuffix = DEFAULT_BEFORE_MESSAGE_SUFFIX;

    private String afterMessagePrefix = DEFAULT_AFTER_MESSAGE_PREFIX;

    private String afterMessageSuffix = DEFAULT_AFTER_MESSAGE_SUFFIX;


    /**
     * Set whether the query string should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeQueryString" in the filter definition in {@code web.xml}.
     */
    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    /**
     * Return whether the query string should be included in the log message.
     */
    protected boolean isIncludeQueryString() {
        return this.includeQueryString;
    }

    /**
     * Set whether the client address and session id should be included in the
     * log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeClientInfo" in the filter definition in {@code web.xml}.
     */
    public void setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
    }

    /**
     * Return whether the client address and session id should be included in the
     * log message.
     */
    protected boolean isIncludeClientInfo() {
        return this.includeClientInfo;
    }

    /**
     * Set whether the request headers should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeHeaders" in the filter definition in {@code web.xml}.
     * @since 4.3
     */
    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    /**
     * Return whether the request headers should be included in the log message.
     * @since 4.3
     */
    protected boolean isIncludeHeaders() {
        return this.includeHeaders;
    }

    /**
     * Set whether the request payload (body) should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includePayload" in the filter definition in {@code web.xml}.
     * @since 3.0
     */
    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    /**
     * Return whether the request payload (body) should be included in the log message.
     * @since 3.0
     */
    protected boolean isIncludePayload() {
        return this.includePayload;
    }

    /**
     * Configure a predicate for selecting which headers should be logged if
     * {@link #setIncludeHeaders(boolean)} is set to {@code true}.
     * <p>By default this is not set in which case all headers are logged.
     * @param headerPredicate the predicate to use
     * @since 5.2
     */
    public void setHeaderPredicate(@Nullable Predicate<String> headerPredicate) {
        this.headerPredicate = headerPredicate;
    }

    /**
     * The configured {@link #setHeaderPredicate(Predicate) headerPredicate}.
     * @since 5.2
     */
    @Nullable
    protected Predicate<String> getHeaderPredicate() {
        return this.headerPredicate;
    }

    /**
     * Set the maximum length of the payload body to be included in the log message.
     * Default is 50 characters.
     * @since 3.0
     */
    public void setMaxPayloadLength(int maxPayloadLength) {
        Assert.isTrue(maxPayloadLength >= 0, "'maxPayloadLength' must be greater than or equal to 0");
        this.maxPayloadLength = maxPayloadLength;
    }

    /**
     * Return the maximum length of the payload body to be included in the log message.
     * @since 3.0
     */
    protected int getMaxPayloadLength() {
        return this.maxPayloadLength;
    }

    /**
     * Set the value that should be prepended to the log message written
     * <i>before</i> a request is processed.
     */
    public void setBeforeMessagePrefix(String beforeMessagePrefix) {
        this.beforeMessagePrefix = beforeMessagePrefix;
    }

    /**
     * Set the value that should be appended to the log message written
     * <i>before</i> a request is processed.
     */
    public void setBeforeMessageSuffix(String beforeMessageSuffix) {
        this.beforeMessageSuffix = beforeMessageSuffix;
    }

    /**
     * Set the value that should be prepended to the log message written
     * <i>after</i> a request is processed.
     */
    public void setAfterMessagePrefix(String afterMessagePrefix) {
        this.afterMessagePrefix = afterMessagePrefix;
    }

    /**
     * Set the value that should be appended to the log message written
     * <i>after</i> a request is processed.
     */
    public void setAfterMessageSuffix(String afterMessageSuffix) {
        this.afterMessageSuffix = afterMessageSuffix;
    }


    /**
     * The default value is "false" so that the filter may log a "before" message
     * at the start of request processing and an "after" message at the end from
     * when the last asynchronously dispatched thread is exiting.
     */
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    /**
     * Forwards the request to the next filter in the chain and delegates down to the subclasses
     * to perform the actual request logging both before and after the request is processed.
     * @see #beforeRequest
     * @see #afterRequest
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
        }

        boolean shouldLog = shouldLog(requestToUse);
        if (shouldLog && isFirstRequest) {
            beforeRequest(requestToUse, getBeforeMessage(requestToUse));
        }
        try {
            filterChain.doFilter(requestToUse, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
        finally {
            if (shouldLog && !isAsyncStarted(requestToUse)) {
                afterRequest(requestToUse, getAfterMessage(requestToUse));
            }
            if(shouldLog && !isAsyncStarted(requestToUse) && shouldLogResponse()){
                responseWrapper.copyBodyToResponse();
                afterResponse(responseWrapper, createResponseMessage(requestToUse, response));
            }
        }
    }

    /**
     * Get the message to write to the log before the request.
     * @see #createMessage
     */
    private String getBeforeMessage(HttpServletRequest request) {
        return createMessage(request, this.beforeMessagePrefix, this.beforeMessageSuffix);
    }

    /**
     * Get the message to write to the log after the request.
     * @see #createMessage
     */
    private String getAfterMessage(HttpServletRequest request) {
        return createMessage(request, this.afterMessagePrefix, this.afterMessageSuffix);
    }

    /**
     * Create a log message for the given request, prefix and suffix.
     * <p>If {@code includeQueryString} is {@code true}, then the inner part
     * of the log message will take the form {@code request_uri?query_string};
     * otherwise the message will simply be of the form {@code request_uri}.
     * <p>The final message is composed of the inner part as described and
     * the supplied prefix and suffix.
     */
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        StringBuilder msg = new StringBuilder();
        msg.append(prefix);
        msg.append(request.getMethod()).append(' ');
        msg.append(request.getRequestURI());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                msg.append('?').append(queryString);
            }
        }
        msg.append(NEW_LINE);

        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                msg.append("client\t> ").append(client).append(NEW_LINE);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                msg.append("session\t> ").append(session.getId()).append(NEW_LINE);
            }
            String user = request.getRemoteUser();
            if (user != null) {
                msg.append("user\t> ").append(user).append(NEW_LINE);
            }
        }

        if (isIncludeHeaders()) {
            HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
            if (getHeaderPredicate() != null) {
                Enumeration<String> names = request.getHeaderNames();
                while (names.hasMoreElements()) {
                    String header = names.nextElement();
                    if (!getHeaderPredicate().test(header)) {
                        headers.set(header, "masked");
                    }
                }
            }
            msg.append("headers\t> ").append(headers).append(NEW_LINE);
        }

        if (isIncludePayload()) {
            String payload = getMessagePayload(request);
            if (payload != null) {
                msg.append("payload\t> ").append(payload).append(NEW_LINE);
            }
        }

        msg.append(suffix);
        return msg.toString();
    }

    /**
     * Extracts the message payload portion of the message created by
     * {@link #createMessage(HttpServletRequest, String, String)} when
     * {@link #isIncludePayload()} returns true.
     * @since 5.0.3
     */
    @Nullable
    protected String getMessagePayload(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper =
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, getMaxPayloadLength());
                try {
                    return new String(buf, 0, length, wrapper.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    return "[unknown]";
                }
            }
        }
        return null;
    }

    /**
     * Extracts the message payload portion of the message created by
     * {@link #createMessage(HttpServletRequest, String, String)} when
     * {@link #isIncludePayload()} returns true.
     * @since 5.0.3
     */
    @Nullable
    protected String getResponse(HttpServletRequest response) {
        return null;
    }

    private String createResponseMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder msg = new StringBuilder();
        msg.append(getResponseMessage(request, response));
        if(isIncludeResponseBody()){
            //TODO : Log response body.
//            msg.append(NEW_LINE)
//                    .append(getResponseBody(response));
        }
        if(isIncludeResponseHeaders()){
            //TODO : Log response headers
        }
        return msg.toString();
    }

    @Nullable
    protected String getResponseMessage(HttpServletRequest request, HttpServletResponse response){
        StringBuilder msg = new StringBuilder();
        msg.append("Server responded with : ")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURI())
                .append(" ")
                .append(response.getStatus());
        return msg.toString();
    }

    //TODO : Correct this. This is giving null
    @Nullable
    protected String getResponseBody(HttpServletResponse httpServletResponse) throws IOException {
        ContentCachingResponseWrapper response = new ContentCachingResponseWrapper(httpServletResponse);
        if (response != null) {
            response.copyBodyToResponse();
            byte[] buf = response.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, getMaxPayloadLength());
                try {
                    return new String(buf, 0, length, response.getCharacterEncoding());
                }
                catch (UnsupportedEncodingException ex) {
                    return "[unknown]";
                }
            }
        }
        return null;
    }

    /**
     * Determine whether to call the {@link #beforeRequest}/{@link #afterRequest}
     * methods for the current request, i.e. whether logging is currently active
     * (and the log message is worth building).
     * <p>The default implementation always returns {@code true}. Subclasses may
     * override this with a log level check.
     * @param request current HTTP request
     * @return {@code true} if the before/after method should get called;
     * {@code false} otherwise
     * @since 4.1.5
     */
    protected boolean shouldLog(HttpServletRequest request) {
        return true;
    }

    protected boolean shouldLogResponse() {
        return isIncludeResponse();
    }

    /**
     * Concrete subclasses should implement this method to write a log message
     * <i>before</i> the request is processed.
     * @param request current HTTP request
     * @param message the message to log
     */
    protected abstract void beforeRequest(HttpServletRequest request, String message);

    /**
     * Concrete subclasses should implement this method to write a log message
     * <i>after</i> the request is processed.
     * @param request current HTTP request
     * @param message the message to log
     */
    protected abstract void afterRequest(HttpServletRequest request, String message);

    protected abstract void afterResponse(HttpServletResponse response, String message);
}

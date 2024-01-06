package com.bill.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

abstract class PostCorsInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // skip cors requests
        final String accessControlRequestMethod = request.getHeader("access-control-request-method");
        if (accessControlRequestMethod != null) {
            return true;
        }
        return preHandleNonCors(request, response, handler);
    }

    abstract boolean preHandleNonCors(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception;
}

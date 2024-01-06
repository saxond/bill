package com.bill.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProtectedResourceInterceptor extends PostCorsInterceptor {
    private final Logger LOGGER = Logger.getLogger(ProtectedResourceInterceptor.class.getName());

    @Override
    public boolean preHandleNonCors(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var principal = AuthenticatedUser.getUserPrincipal(request);
        if (principal.isPresent()) {
            if (principal.get().isAuthenticated()) {
                return true;
            }
            LOGGER.log(Level.INFO, "Unauthorized user " + principal);
        } else {
            LOGGER.log(Level.INFO, "Unauthenticated user");
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return true;
    }
}

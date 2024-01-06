package com.bill.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProtectedResourceInterceptor extends PostCorsInterceptor {
    private final Logger LOGGER = Logger.getLogger(ProtectedResourceInterceptor.class.getName());

    @Override
    public boolean preHandleNonCors(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final User user = (User) request.getAttribute(AuthenticationInterceptor.USER_KEY);
        if (user != null) {
            if (user.isEnabled()) {
                return true;
            }
            LOGGER.log(Level.INFO, "Unauthorized user " + user.getEmail());
        } else {
            LOGGER.log(Level.INFO, "Unauthenticated user");
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return true;
    }
}

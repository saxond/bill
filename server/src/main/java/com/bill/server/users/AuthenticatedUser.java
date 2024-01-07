package com.bill.server.users;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthenticatedUser extends Authentication {
    static Optional<AuthenticatedUser> getUserPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getUserPrincipal()).filter(AuthenticatedUser.class::isInstance)
                .map(AuthenticatedUser.class::cast);
    }

    default User getUser() {
        return (User) getPrincipal();
    }
}

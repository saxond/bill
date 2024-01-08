package com.bill.server.users;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequestMapping(value = "/users", produces = "application/json")
@CrossOrigin
@RestController
public class UsersController {

    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/me")
    public User.SafeUser get(HttpServletRequest request) {
        if (request.getUserPrincipal() instanceof OAuth2AuthenticationToken) {
            OAuth2User principal = ((OAuth2AuthenticationToken) request.getUserPrincipal()).getPrincipal();
            System.err.println(principal.getName());
            return new User.SafeUser(principal.getAttribute("name"), true);
        }
        final Optional<User> user = AuthenticatedUser.getUserPrincipal(request).map(AuthenticatedUser::getUser);
        return user.map(User::safe).orElseThrow();
    }
}

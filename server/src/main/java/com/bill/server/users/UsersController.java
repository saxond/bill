package com.bill.server.users;

import jakarta.servlet.http.HttpServletRequest;
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
        final Optional<User> user = AuthenticatedUser.getUserPrincipal(request).map(AuthenticatedUser::getUser);
        return user.map(User::safe).orElseThrow();
    }
}

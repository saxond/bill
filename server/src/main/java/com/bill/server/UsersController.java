package com.bill.server;

import com.google.api.services.oauth2.model.Userinfo;
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
        final Optional<Userinfo> userinfo = Optional
                .ofNullable((Userinfo) request.getAttribute(AuthenticationInterceptor.USER_INFO_KEY));
        return userinfo.flatMap(u -> userRepository.findById(u.getEmail()).map(User::safe)).orElseThrow();
    }
}

package com.bill.server;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Component
@Order(1)
public class AuthenticationInterceptor extends PostCorsInterceptor {
    static final String USER_INFO_KEY = "USER_INFO";
    static final String USER_KEY = "USER";
    private final Logger LOGGER = Logger.getLogger(AuthenticationInterceptor.class.getName());
    private final UserRepository userRepository;

    public AuthenticationInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    boolean preHandleNonCors(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String accessToken = request.getHeader("access-token");
        if (accessToken == null) {
            final Iterable<String> headers = () -> request.getHeaderNames().asIterator();
            LOGGER.log(Level.SEVERE, String.format("No access token for url: %s, headers: %s", request.getRequestURI(),
                    StreamSupport.stream(headers.spliterator(), false).toList()));
        } else {
            final GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
            final Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential)
                    .setApplicationName("Oauth2").build();

            try {
                final Userinfo userinfo = oauth2.userinfo().get().execute();
                request.setAttribute(USER_INFO_KEY, userinfo);
                LOGGER.log(Level.INFO, "Authenticated {0}", userinfo.getEmail());

                final Optional<User> user = userRepository.findById(userinfo.getEmail());
                if (user.isPresent()) {
                    request.setAttribute(USER_KEY, user.get());
                } else {
                    User newUser = User.from(userinfo);
                    userRepository.save(newUser);
                    LOGGER.log(Level.INFO, "Create user record for {0}", userinfo.getEmail());
                    request.setAttribute(USER_KEY, newUser);
                }

            } catch (GoogleJsonResponseException e) {
                LOGGER.log(Level.FINE, "Auth error: {0}", e.getDetails());
            } catch (IOException e) {
                LOGGER.log(Level.FINE, "Auth io error: {0}", e);
            }
        }
        return true;
    }
}

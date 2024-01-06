package com.bill.server;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Component
public class AuthenticationInterceptor extends PostCorsInterceptor {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationInterceptor.class.getName());
    private final UserRepository userRepository;
    static final BiConsumer<HttpServletRequest, Authentication> PRINCIPAL_SETTER = createPrincipalSetter();

    public AuthenticationInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    boolean preHandleNonCors(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LOGGER.info(request::getRequestURI);
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
                LOGGER.log(Level.INFO, "Authenticated {0}", userinfo.getEmail());

                final Optional<User> user = userRepository.findById(userinfo.getEmail());
                if (user.isPresent()) {
                    setAuthentication(request, user.get().asAuthentication());
                } else {
                    User newUser = User.from(userinfo);
                    userRepository.save(newUser);
                    LOGGER.log(Level.INFO, "Create user record for {0}", userinfo.getEmail());
                    setAuthentication(request, newUser.asAuthentication());
                }

            } catch (GoogleJsonResponseException e) {
                LOGGER.log(Level.FINE, "Auth error: {0}", e.getDetails());
            } catch (IOException e) {
                LOGGER.log(Level.FINE, "Auth io error: {0}", e);
            }
        }
        return true;
    }

    private static void setAuthentication(HttpServletRequest request, Authentication authentication) {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        PRINCIPAL_SETTER.accept(request, authentication);
    }

    private static BiConsumer<HttpServletRequest, Authentication> createPrincipalSetter() {
        try {
            final var field = RequestFacade.class.getDeclaredField("request");
            field.setAccessible(true);
            return (request, authentication) -> {
                try {
                    final Request req = (Request) field.get(request);
                    req.setUserPrincipal(authentication);
                } catch (IllegalAccessException e) {
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
            };
        } catch (NoSuchFieldException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
            // SAD no op
            return (r, p) -> {
            };
        }
    }
}

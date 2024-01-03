package com.bill.server;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.StreamSupport;

@Component
@Order(1)
public class AuthenticationFilter implements Filter {
    private final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void doFilter(
            ServletRequest req,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        final String accessControlRequestMethod = request.getHeader("access-control-request-method");
        if (accessControlRequestMethod != null) {
            chain.doFilter(req, response);
            return;
        }
        final String accessToken = request.getHeader("access-token");
        if (accessToken == null) {
            final Iterable<String> headers = () -> request.getHeaderNames().asIterator();
            LOGGER.log(Level.SEVERE, String.format("No access token for url: %s, headers: %s", request.getRequestURI(),
                    StreamSupport.stream(headers.spliterator(), false).toList()));
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        } else {
            final Iterable<String> headers = () -> request.getHeaderNames().asIterator();

            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
            Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
                    "Oauth2").build();

            try {
                Userinfo userinfo = oauth2.userinfo().get().execute();
                LOGGER.log(Level.INFO, "Authenticated {0}", userinfo.getEmail());
                chain.doFilter(req, response);
                return;
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}

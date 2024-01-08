package com.bill.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class OAuth2LoginSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        // .oauth2Login(oAuthConfig -> oAuthConfig.)
        /*
         * .oauth2Login(oAuthConfig -> { oAuthConfig.successHandler((req, res, auth) ->
         * { var referrer = req.getHeader("referrer"); System.out.println(auth);
         * res.getOutputStream().println("Dude!");
         * 
         * }).failureHandler((req, res, ex) -> { ex.printStackTrace(); }); });
         */
        return http.build();
    }
}

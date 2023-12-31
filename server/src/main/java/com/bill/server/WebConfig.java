package com.bill.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authInterceptor;
    private final ProtectedResourceInterceptor protectedResourceInterceptor;

    public WebConfig(AuthenticationInterceptor authInterceptor,
            ProtectedResourceInterceptor protectedResourceInterceptor) {
        this.authInterceptor = authInterceptor;
        this.protectedResourceInterceptor = protectedResourceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns("/error");
        registry.addInterceptor(protectedResourceInterceptor).excludePathPatterns("/users/me", "/error");
    }
}

package com.rocha_inf.yasmimqueiroznails.security.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

public final class AuthorizationConfig {

    private AuthorizationConfig(){}

    public static void configure(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request){

        request
                .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/verify-email").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();

        request
                .requestMatchers("/blocks").hasRole("PROFESSIONAL");

        request
                .anyRequest().authenticated();
    }

}

package com.rocha_inf.yasmimqueiroznails.security.jwt.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public Algorithm algorithm(JwtPropertiesConfig jwtPropertiesConfig) {
        return Algorithm.HMAC256(jwtPropertiesConfig.secret());
    }

}

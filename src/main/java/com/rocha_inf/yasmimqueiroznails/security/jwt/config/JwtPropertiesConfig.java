package com.rocha_inf.yasmimqueiroznails.security.jwt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtPropertiesConfig(
        String secret,
        String issuer
){}
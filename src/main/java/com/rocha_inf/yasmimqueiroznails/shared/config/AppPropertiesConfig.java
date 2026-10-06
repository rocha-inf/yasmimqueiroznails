package com.rocha_inf.yasmimqueiroznails.shared.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppPropertiesConfig(Url url) {

    public record Url(String backend, String frontend){}

}

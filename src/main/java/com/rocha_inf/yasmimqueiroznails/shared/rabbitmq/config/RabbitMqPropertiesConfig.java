package com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rabbitmq")
public record RabbitMqPropertiesConfig(Email email) {

    public record Email(String exchange, Welcome welcome, Verification verification) {

        public record Welcome(String queue, String routingKey) {}

        public record Verification(String queue, String routingKey) {}

    }

}

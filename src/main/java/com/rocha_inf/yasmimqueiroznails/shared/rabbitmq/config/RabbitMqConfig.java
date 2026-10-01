package com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private final RabbitMqPropertiesConfig mqPropertiesConfig;

    public RabbitMqConfig(RabbitMqPropertiesConfig mqPropertiesConfig) {
        this.mqPropertiesConfig = mqPropertiesConfig;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange emailExchange(){
        return ExchangeBuilder
                .topicExchange(mqPropertiesConfig.email().exchange())
                .durable(true)
                .build();
    }

    @Bean
    public Queue emailWelcomeQueue(){
        return QueueBuilder
                .durable(mqPropertiesConfig.email().welcome().queue())
                .build();
    }

    @Bean
    public Binding emailBinding(Queue emailWelcomeQueue, TopicExchange emailExchange){
        return BindingBuilder
                .bind(emailWelcomeQueue)
                .to(emailExchange)
                .with(mqPropertiesConfig.email().welcome().routingKey());
    }

}

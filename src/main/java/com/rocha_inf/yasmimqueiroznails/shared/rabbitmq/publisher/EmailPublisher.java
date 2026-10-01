package com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.publisher;

import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.config.RabbitMqPropertiesConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqPropertiesConfig mqPropertiesConfig;

    public EmailPublisher(RabbitTemplate rabbitTemplate, RabbitMqPropertiesConfig mqPropertiesConfig) {
        this.rabbitTemplate = rabbitTemplate;
        this.mqPropertiesConfig = mqPropertiesConfig;
    }

    public void publishWelcomeEmail(WelcomeEmailMessage welcomeEmailMessage){
        rabbitTemplate.convertAndSend(
                mqPropertiesConfig.email().exchange(),
                mqPropertiesConfig.email().welcome().routingKey(),
                welcomeEmailMessage);
    }

}

package com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.publisher;

import com.rocha_inf.yasmimqueiroznails.security.auth.message.VerificationEmailMessage;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.config.RabbitMqPropertiesConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqPropertiesConfig mqPropertiesConfig;

    public void publishWelcomeEmail(WelcomeEmailMessage welcomeEmailMessage){
        rabbitTemplate.convertAndSend(
                mqPropertiesConfig.email().exchange(),
                mqPropertiesConfig.email().welcome().routingKey(),
                welcomeEmailMessage);
    }

    public void publishVerificationEmail(VerificationEmailMessage verificationEmailMessage){
        rabbitTemplate.convertAndSend(
                mqPropertiesConfig.email().exchange(),
                mqPropertiesConfig.email().verification().routingKey(),
                verificationEmailMessage
        );
    }
}

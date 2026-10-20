package com.rocha_inf.yasmimqueiroznails.email.consumer;

import com.rocha_inf.yasmimqueiroznails.email.service.EmailService;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.VerificationEmailMessage;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService service;

    @RabbitListener(queues = "email.welcome.queue")
    public void consumeWelcomeEmail(WelcomeEmailMessage message) {
        service.sendWelcomeEmail(message);
    }

    @RabbitListener(queues = "email.verification.queue")
    public void consumeVerificationEmail(VerificationEmailMessage message){
        service.sendVerificationEmail(message);
    }

}

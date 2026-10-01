package com.rocha_inf.yasmimqueiroznails.email.consumer;

import com.rocha_inf.yasmimqueiroznails.email.service.EmailService;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;

    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email.welcome.queue")
    public void consumeWelcomeEmail(WelcomeEmailMessage message) {
        System.out.println("Email received: " + message.email());
        emailService.sendWelcomeEmail(message);
    }

}

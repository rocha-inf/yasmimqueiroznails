package com.rocha_inf.yasmimqueiroznails.email.consumer;

import com.rocha_inf.yasmimqueiroznails.email.service.EmailService;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.VerificationEmailMessage;
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
        emailService.sendWelcomeEmail(message);
    }

    @RabbitListener(queues = "email.verification.queue")
    public void consumeVerificationEmail(VerificationEmailMessage message){
        emailService.sendVerificationEmail(message);
    }

}

package com.rocha_inf.yasmimqueiroznails.email.service;

import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(String to, String subject, String body){

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(this.from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

    }

    public void sendWelcomeEmail(WelcomeEmailMessage message){
        String subject = "Boas vindas, " + message.firstName() + "!";
        send(message.email(), subject, "Mensagem de boas vindas");
    }

}

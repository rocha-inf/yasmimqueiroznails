package com.rocha_inf.yasmimqueiroznails.email.service;

import com.rocha_inf.yasmimqueiroznails.security.auth.message.VerificationEmailMessage;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender javaMailSender;

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
        String body = "Mensagem de boas vindas";
        this.send(message.email(), subject, body);
    }

    public void sendVerificationEmail(VerificationEmailMessage message){
        String subject = "Verificação de e-mail";
        String body = "Clique no link para verificar seu e-mail: " + message.verificationURL();
        this.send(message.email(), subject, body);
    }

}

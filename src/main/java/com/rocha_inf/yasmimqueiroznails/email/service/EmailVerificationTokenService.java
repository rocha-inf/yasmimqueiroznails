package com.rocha_inf.yasmimqueiroznails.email.service;

import com.rocha_inf.yasmimqueiroznails.email.entity.EmailVerificationToken;
import com.rocha_inf.yasmimqueiroznails.email.exception.InvalidTokenException;
import com.rocha_inf.yasmimqueiroznails.email.exception.TokenAlredyUsedException;
import com.rocha_inf.yasmimqueiroznails.email.exception.TokenExpiredException;
import com.rocha_inf.yasmimqueiroznails.email.repository.EmailVerificationTokenRepository;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.VerificationEmailMessage;
import com.rocha_inf.yasmimqueiroznails.security.auth.message.WelcomeEmailMessage;
import com.rocha_inf.yasmimqueiroznails.security.token.EmailVerificationTokenGenerator;
import com.rocha_inf.yasmimqueiroznails.security.token.TokenHashGenerator;
import com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.publisher.EmailPublisher;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.exception.UserNotFoundException;
import com.rocha_inf.yasmimqueiroznails.user.mapstruct.UserMapper;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailVerificationTokenService {

    private final EmailVerificationTokenGenerator tokenGenerator;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final TokenHashGenerator tokenHashGenerator;
    private final EmailPublisher emailPublisher;
    private final UserMapper userMapper;

    public EmailVerificationTokenService(EmailVerificationTokenGenerator tokenGenerator, EmailVerificationTokenRepository emailVerificationTokenRepository, UserRepository userRepository, TokenHashGenerator tokenHashGenerator, EmailPublisher emailPublisher, UserMapper userMapper) {
        this.tokenGenerator = tokenGenerator;
        this.emailVerificationTokenRepository = emailVerificationTokenRepository;
        this.userRepository = userRepository;
        this.tokenHashGenerator = tokenHashGenerator;
        this.emailPublisher = emailPublisher;
        this.userMapper = userMapper;
    }

    public EmailVerificationToken create(RegisterResponse userRegister){

        User user = userRepository.findById(userRegister.id()).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        String token = tokenGenerator.generate();
        String url = "http://localhost:8080/auth/verify-email?token=" + token;

        String tokenHash = tokenHashGenerator.hash(token);
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user, tokenHash);

        emailPublisher.publishVerificationEmail(new VerificationEmailMessage(userRegister.email(), url));

        return emailVerificationTokenRepository.save(emailVerificationToken);

    }

    public void verifyEmail(String token){

        String tokenHash = tokenHashGenerator.hash(token);
        EmailVerificationToken emailVerificationToken = emailVerificationTokenRepository.findByToken(tokenHash).orElseThrow(() -> new InvalidTokenException("Token inválido"));

        if (emailVerificationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Token expirado");
        }

        if (emailVerificationToken.getUsedAt() != null) {
            throw new TokenAlredyUsedException("Token já utilizado");
        }

        User user = emailVerificationToken.getUser();
        user.activate();
        userRepository.save(user);

        emailVerificationToken.markAsUsed();
        emailVerificationTokenRepository.save(emailVerificationToken);

        WelcomeEmailMessage welcomeEmailMessage = userMapper.toWelcomeEmailMessage(user);
        emailPublisher.publishWelcomeEmail(welcomeEmailMessage);

    }


}

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
import com.rocha_inf.yasmimqueiroznails.shared.config.AppPropertiesConfig;
import com.rocha_inf.yasmimqueiroznails.shared.rabbitmq.publisher.EmailPublisher;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.exception.UserNotFoundException;
import com.rocha_inf.yasmimqueiroznails.user.mapstruct.UserMapper;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenService {

    private final EmailVerificationTokenGenerator tokenGenerator;
    private final EmailVerificationTokenRepository repository;
    private final UserRepository userRepository;
    private final TokenHashGenerator tokenHashGenerator;
    private final EmailPublisher emailPublisher;
    private final UserMapper userMapper;
    private final AppPropertiesConfig appPropertiesConfig;


    public EmailVerificationToken create(RegisterResponse userRegister){

        User user = userRepository.findById(userRegister.id()).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        String token = tokenGenerator.generate();
        String url = appPropertiesConfig.url().backend() + "?token=" + token;

        String tokenHash = tokenHashGenerator.hash(token);
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user, tokenHash);

        emailPublisher.publishVerificationEmail(new VerificationEmailMessage(userRegister.email(), url));

        return repository.save(emailVerificationToken);

    }

    public void verifyEmail(String token){

        String tokenHash = tokenHashGenerator.hash(token);
        EmailVerificationToken emailVerificationToken = repository.findByToken(tokenHash).orElseThrow(() -> new InvalidTokenException("Token inválido"));

        this.validate(emailVerificationToken);

        User user = emailVerificationToken.getUser();
        user.activate();
        userRepository.save(user);

        emailVerificationToken.markAsUsed();
        repository.save(emailVerificationToken);

        WelcomeEmailMessage welcomeEmailMessage = userMapper.toWelcomeEmailMessage(user);
        emailPublisher.publishWelcomeEmail(welcomeEmailMessage);

    }

    private void validate(EmailVerificationToken emailVerificationToken){

        if (emailVerificationToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new TokenExpiredException("Token expirado");
        }

        if (emailVerificationToken.getUsedAt() != null) {
            throw new TokenAlredyUsedException("Token já utilizado");
        }
    }

}

package com.rocha_inf.yasmimqueiroznails.security.auth.service;


import com.rocha_inf.yasmimqueiroznails.email.exception.InvalidTokenException;
import com.rocha_inf.yasmimqueiroznails.email.service.EmailVerificationTokenService;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.request.LoginRequest;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.LoginResponse;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.enums.UserStatus;
import com.rocha_inf.yasmimqueiroznails.user.exception.EmailAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.user.exception.PhoneNumberAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.user.mapstruct.UserMapper;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import com.rocha_inf.yasmimqueiroznails.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailVerificationTokenService emailVerificationTokenService;

    public RegisterResponse register(RegisterRequest request) throws EmailAlreadyExistsException, PhoneNumberAlreadyExistsException {

        this.validateRegister(request);
        String encodedPassword = passwordEncoder.encode(request.password());

        User user = userMapper.toEntity(request, encodedPassword);
        RegisterResponse response = userMapper.toRegisterResponse(userRepository.save(user));
        emailVerificationTokenService.create(response);

        return response;
    }

    private void validateRegister(RegisterRequest request){

        if (userRepository.existsByEmailAndStatusNot(request.email(), UserStatus.DELETED)){
            throw new EmailAlreadyExistsException("E-mail já cadastrado");
        }

        if (userRepository.existsByPhoneNumberAndStatusNot(request.phoneNumber(), UserStatus.DELETED)){
            throw new PhoneNumberAlreadyExistsException("Número de telefone já cadastrado");
        }
    }


    public LoginResponse login(LoginRequest request) {

        UsernamePasswordAuthenticationToken userAndPasswordToken = new UsernamePasswordAuthenticationToken(
                request.email(), request.password()
        );

        Authentication authentication = authenticationManager.authenticate(userAndPasswordToken);
        User user = (User) Objects.requireNonNull(authentication.getPrincipal(), "O usuário autenticado não pode ser nulo");
        String token = jwtService.generateToken(user.getId());

        return new LoginResponse(token);
    }


    public void verifyEmail(String token){

        if (token == null || token.isBlank()){
            throw new InvalidTokenException("Token não pode ser nulo ou vazio");
        }

        emailVerificationTokenService.verifyEmail(token);
    }

}

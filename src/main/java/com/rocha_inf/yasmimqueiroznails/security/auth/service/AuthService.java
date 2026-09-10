package com.rocha_inf.yasmimqueiroznails.security.auth.service;

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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public RegisterResponse register(RegisterRequest request) throws EmailAlreadyExistsException, PhoneNumberAlreadyExistsException {

        if (userRepository.existsByEmailAndStatusNot(request.email(), UserStatus.DELETED)){
            throw new EmailAlreadyExistsException("E-mail já cadastrado");
        }

        if (userRepository.existsByPhoneNumberAndStatusNot(request.phoneNumber(), UserStatus.DELETED)){
            throw new PhoneNumberAlreadyExistsException("Número de telefone já cadastrado");
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        User user = userMapper.toEntity(request, encodedPassword);
        User savedUser = userRepository.save(user);

        return userMapper.toRegisterResponse(savedUser);

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

}

package com.rocha_inf.yasmimqueiroznails.security.auth.service;

import com.rocha_inf.yasmimqueiroznails.config.SecurityConfig;
import com.rocha_inf.yasmimqueiroznails.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.entity.User;
import com.rocha_inf.yasmimqueiroznails.enums.UserStatus;
import com.rocha_inf.yasmimqueiroznails.exception.EmailAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.exception.PhoneNumberAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.mapstruct.UserMapper;
import com.rocha_inf.yasmimqueiroznails.repository.UserRepository;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.request.RegisterRequest;
import com.rocha_inf.yasmimqueiroznails.security.auth.dto.respose.RegisterResponse;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.enums.UserStatus;
import com.rocha_inf.yasmimqueiroznails.user.exception.EmailAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.user.exception.PhoneNumberAlreadyExistsException;
import com.rocha_inf.yasmimqueiroznails.user.mapstruct.UserMapper;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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

}

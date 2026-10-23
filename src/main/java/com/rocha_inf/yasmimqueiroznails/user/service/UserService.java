package com.rocha_inf.yasmimqueiroznails.user.service;

import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.enums.UserStatus;
import com.rocha_inf.yasmimqueiroznails.user.exception.UserNotFoundException;
import com.rocha_inf.yasmimqueiroznails.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByIdAndStatusActive(UUID id){
        return repository.findByIdAndStatus(id, UserStatus.ACTIVE).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

}

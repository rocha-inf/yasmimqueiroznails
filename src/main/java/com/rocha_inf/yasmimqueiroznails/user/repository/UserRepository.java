package com.rocha_inf.yasmimqueiroznails.user.repository;

import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailAndStatusNot(String email, UserStatus status);

    boolean existsByPhoneNumberAndStatusNot(String phoneNumber, UserStatus status);

}

package com.rocha_inf.yasmimqueiroznails.user.repository;

import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import com.rocha_inf.yasmimqueiroznails.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmailAndStatusNot(String email, UserStatus status);

    boolean existsByPhoneNumberAndStatusNot(String phoneNumber, UserStatus status);

    Optional<UserDetails> findUserByEmail(String email);

    Optional<User> findUserByIdAndDeletedAtIsNull(UUID id);

}

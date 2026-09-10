package com.rocha_inf.yasmimqueiroznails.security.user;

import com.rocha_inf.yasmimqueiroznails.user.enums.Role;

import java.util.UUID;

public record AuthenticatedUser(
        UUID id,
        String email,
        Role role
) {
}

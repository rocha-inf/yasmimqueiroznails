package com.rocha_inf.yasmimqueiroznails.dto.respose;

import com.rocha_inf.yasmimqueiroznails.enums.Role;

import java.util.UUID;

public record RegisterResponse(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        Role role
) {
}

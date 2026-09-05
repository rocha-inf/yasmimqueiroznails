package com.rocha_inf.yasmimqueiroznails.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Primeiro nome é obrigatório")
        String firstName,

        @NotBlank(message = "Último nome é obrigatório")
        String lastName,

        @NotBlank(message = "Número de telefone é obrigatório")
        String phoneNumber,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Senha é obrigatória")
        String password
) {
}

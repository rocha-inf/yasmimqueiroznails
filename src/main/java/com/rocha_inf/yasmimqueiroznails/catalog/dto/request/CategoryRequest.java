package com.rocha_inf.yasmimqueiroznails.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Nome é obrigatória")
        String name,
        String description
) {
}

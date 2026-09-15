package com.rocha_inf.yasmimqueiroznails.scheduling.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BlockOnceRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "Data de inicio do bloqueio é obrigatório")
        LocalDateTime startsAt,

        @NotNull(message = "Data de fim do bloqueio é obrigatório")
        LocalDateTime endsAt
) {
}

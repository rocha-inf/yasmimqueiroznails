package com.rocha_inf.yasmimqueiroznails.catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;


public record OfferingRequest(

        @NotNull(message = "Categoria é obrigatória")
        UUID categoryId,

        @NotBlank(message = "Nome é obrigatório")
        String name,

        String description,

        @NotNull(message = "Preço é obrigatório")
        BigDecimal price,

        @NotNull(message = "Duração é obrigatória")
        Integer durationMinutes
) {
}

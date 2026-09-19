package com.rocha_inf.yasmimqueiroznails.catalog.dto.response;


import java.math.BigDecimal;
import java.util.UUID;

public record OfferingResponse(
        UUID id,
        UUID categoryId,
        String name,
        BigDecimal price,
        Integer durationMinutes
) {
}

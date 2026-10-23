package com.rocha_inf.yasmimqueiroznails.catalog.dto.request;

import com.rocha_inf.yasmimqueiroznails.shared.normalization.StringNormalizer;

import java.math.BigDecimal;
import java.util.UUID;

public record OfferingUpdateRequest(
        UUID categoryId,
        String name,
        String description,
        BigDecimal price,
        Integer durationMinutes
) {
    public OfferingUpdateRequest{
        name = StringNormalizer.normalize(name);
    }
}
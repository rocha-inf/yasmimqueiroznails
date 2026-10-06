package com.rocha_inf.yasmimqueiroznails.block.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime startsAt,
        LocalDateTime endsAt
) {
}

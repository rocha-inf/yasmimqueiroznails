package com.rocha_inf.yasmimqueiroznails.scheduling.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record BlockOnceResponse(
        UUID id,
        String name,
        String description,
        LocalDateTime startsAt,
        LocalDateTime endsAt
) {
}

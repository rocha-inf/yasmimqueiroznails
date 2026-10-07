package com.rocha_inf.yasmimqueiroznails.setting.dto.response;

import java.time.DayOfWeek;
import java.util.UUID;

public record WorkingHourResponse(
        UUID id,
        DayOfWeek dayOfWeek,
        String startsAt,
        String endsAt
) {
}

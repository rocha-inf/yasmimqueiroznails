package com.rocha_inf.yasmimqueiroznails.setting.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record WorkingHourRequest(

        @NotNull(message = "Dia da semana é obrigatório")
        DayOfWeek dayOfWeek,

        @NotNull(message = "Horário de início é obrigatório")
        LocalTime startsAt,

        @NotNull(message = "Horário de término é obrigatório")
        LocalTime endsAt
) {
}

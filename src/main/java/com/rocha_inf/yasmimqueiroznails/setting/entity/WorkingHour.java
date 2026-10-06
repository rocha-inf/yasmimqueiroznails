package com.rocha_inf.yasmimqueiroznails.setting.entity;

import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractSoftDeleteEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "working_hour")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class WorkingHour extends AbstractSoftDeleteEntity {

    @NotNull(message = "Dia da semana é obrigatório")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "day_of_week", nullable = false, columnDefinition = "day_of_week")
    private DayOfWeek dayOfWeek;

    @NotNull(message = "Hora de início é obrigatória")
    @Column(name = "starts_at", nullable = false)
    private LocalTime startsAt;

    @NotNull(message = "Hora de término é obrigatória")
    @Column(name = "ends_at", nullable = false)
    private LocalTime endsAt;

    public WorkingHour(DayOfWeek dayOfWeek, LocalTime startsAt, LocalTime endsAt) {
        this.id = UUID.randomUUID();
        this.dayOfWeek = dayOfWeek;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WorkingHour workingHour)) return false;
        return Objects.equals(this.id, workingHour.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkingHour{" +
                "id=" + id +
                ", dayOfWeek=" + dayOfWeek +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

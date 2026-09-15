package com.rocha_inf.yasmimqueiroznails.scheduling.entity;

import com.rocha_inf.yasmimqueiroznails.scheduling.entity.pk.BlockRecurringDayId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Entity
@Table(name = "block_recurring_day")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BlockRecurringDay {

    @EmbeddedId
    private BlockRecurringDayId blockRecurringDayId;

    @MapsId("blockRecurringId")
    @NotNull(message = "Id de Bloqueio Recorrente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "block_recurring_id", nullable = false)
    private BlockRecurring blockRecurring;

    @MapsId("dayOfWeekValue")
    @NotNull(message = "Dia da semana é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "day_of_week_id", nullable = false)
    private DayOfWeek dayOfWeek;

    public BlockRecurringDay(BlockRecurring blockRecurring, DayOfWeek dayOfWeek) {
        this.blockRecurringDayId = new BlockRecurringDayId(blockRecurring.getId(), dayOfWeek.getValue());
        this.blockRecurring = blockRecurring;
        this.blockRecurring.addDayOfWeek(this);
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BlockRecurringDay that)) return false;
        return Objects.equals(blockRecurringDayId, that.blockRecurringDayId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(blockRecurringDayId);
    }

    @Override
    public String toString() {
        return "BlockRecurringDay{" +
                "blockRecurringDayId=" + blockRecurringDayId +
                '}';
    }
}

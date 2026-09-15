package com.rocha_inf.yasmimqueiroznails.scheduling.entity.pk;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockRecurringDayId {

    private UUID blockRecurringId;
    private Short dayOfWeekValue;

    public BlockRecurringDayId(UUID blockRecurringId, Short dayOfWeekValue) {
        this.blockRecurringId = blockRecurringId;
        this.dayOfWeekValue = dayOfWeekValue;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BlockRecurringDayId that)) return false;
        return Objects.equals(blockRecurringId, that.blockRecurringId) && Objects.equals(dayOfWeekValue, that.dayOfWeekValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockRecurringId, dayOfWeekValue);
    }

    @Override
    public String toString() {
        return "BlockRecurringDayId{" +
                "blockRecurringId=" + blockRecurringId +
                ", dayOfWeekValue=" + dayOfWeekValue +
                '}';
    }
}

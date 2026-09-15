package com.rocha_inf.yasmimqueiroznails.scheduling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "block_recurring")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class BlockRecurring extends AbstractBlockEntity{

    @NotNull(message = "Horário de inicio do bloqueio é obrigatório")
    @Column(name = "starts_at", nullable = false)
    private LocalTime startsAt;

    @NotNull(message = "Horário de fim do bloqueio é obrigatório")
    @Column(name = "ends_at", nullable = false)
    private LocalTime endsAt;

    @NotNull(message = "Verificação de fim de horário é obrigatório")
    @Column(name = "ends_on_next_day", nullable = false)
    private Boolean endsOnNextDay;

    @OneToMany(mappedBy = "blockRecurring")
    private Set<BlockRecurringDay> daysOfWeek;

    public BlockRecurring(String name, String description, LocalTime startsAt, LocalTime endsAt, Boolean endsOnNextDay) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.endsOnNextDay = endsOnNextDay != null && endsOnNextDay;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BlockRecurring blockRecurring)) return false;
        return Objects.equals(this.id, blockRecurring.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BlockRecurring{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", endsOnNextDay=" + endsOnNextDay +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }

    public void addDayOfWeek(BlockRecurringDay blockRecurringDay) {
        daysOfWeek.add(blockRecurringDay);
    }

    public void removeDayOfWeek(BlockRecurringDay blockRecurringDay) {
        daysOfWeek.remove(blockRecurringDay);
    }
}

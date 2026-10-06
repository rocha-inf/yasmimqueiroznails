package com.rocha_inf.yasmimqueiroznails.block.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "block")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Block extends AbstractBlockEntity {

    @NotNull(message = "Data de inicio do bloqueio é obrigatório")
    @Future(message = "Data de inicio do bloqueio deve ser posterior ao tempo presente")
    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @NotNull(message = "Data de fim do bloqueio é obrigatório")
    @Future(message = "Data de fim do bloqueio deve ser posterior ao tempo presente")
    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;

    public Block(String name, String description, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Block block)) return false;
        return Objects.equals(this.id, block.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startsAt=" + startsAt +
                ", endsAt=" + endsAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

package com.rocha_inf.yasmimqueiroznails.catalog.entity;

import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractSoftDeleteEntity;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "offering")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Offering extends AbstractSoftDeleteEntity {

    @NotBlank(message = "Criador de Offering é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;

    @NotNull(message = "Categoria é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotBlank(message = "Nome é obrigatório")
    @Length(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Preço é obrigatória")
    @PositiveOrZero
    @Digits(integer = 8, fraction = 2, message = "O preço deve ter no máximo 8 digitos inteiros e 2 casas decimais")
    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Duração em minutos é obrigatório")
    @Max(value = 300, message = "Duração deve ser ter no máximo 300 minutos (5 horas)")
    @Min(value = 1, message = "Duração deve ser maior que 1 minuto")
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    public Offering(User createdBy, Category category, String name, String description, BigDecimal price, Integer durationMinutes) {
        this.id = UUID.randomUUID();
        this.createdBy = createdBy;
        this.category = category;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Offering offering)) return false;
        return Objects.equals(this.id, offering.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Offering{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", durationMinutes=" + durationMinutes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

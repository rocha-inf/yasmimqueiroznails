package com.rocha_inf.yasmimqueiroznails.scheduling.entity;

import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractSoftDeleteEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "appointment_offering")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AppointmentOffering extends AbstractSoftDeleteEntity {

    @NotNull(message = "Id de agendamento é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;

    @NotNull(message = "Id do Serviço é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "offering_id", nullable = false)
    private Offering offering;

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

    public AppointmentOffering(Appointment appointment, Offering offering, BigDecimal price, Integer durationMinutes) {
        this.id = UUID.randomUUID();
        this.appointment = appointment;
        this.offering = offering;
        this.price = price;
        this.durationMinutes = durationMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AppointmentOffering appointmentOffering)) return false;
        return Objects.equals(this.id, appointmentOffering.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AppointmentOffering{" +
                "id=" + id +
                ", appointment=" + appointment +
                ", offering=" + offering +
                ", price=" + price +
                ", durationMinutes=" + durationMinutes +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

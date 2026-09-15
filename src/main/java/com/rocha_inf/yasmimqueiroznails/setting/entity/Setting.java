package com.rocha_inf.yasmimqueiroznails.setting.entity;

import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "setting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Setting extends AbstractBaseEntity {

    @NotNull(message = "Campo de aprovação automática de agendamentos é obrigatório")
    @Column(name = "auto_approve_appointments", nullable = false)
    private Boolean autoApproveAppointments;

    public Setting(Boolean autoApproveAppointments) {
        this.id = UUID.randomUUID();
        this.autoApproveAppointments = autoApproveAppointments != null && autoApproveAppointments;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Setting setting)) return false;
        return Objects.equals(this.id, setting.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", autoApproveAppointments=" + autoApproveAppointments +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

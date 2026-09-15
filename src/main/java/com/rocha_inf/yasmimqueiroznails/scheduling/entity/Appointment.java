package com.rocha_inf.yasmimqueiroznails.scheduling.entity;

import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import com.rocha_inf.yasmimqueiroznails.scheduling.enums.AppointmentStatus;
import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractBaseEntity;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "appointment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Appointment extends AbstractBaseEntity {

    @NotNull(message = "Criador de Offering é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User createdBy;

    @NotNull(message = "Status do agendamento é obrigatório")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "appointment_status")
    private AppointmentStatus status;

    @NotNull(message = "Horário do agendamento é obrigatório")
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "user_notes", columnDefinition = "text")
    private String userNotes;

    @Column(name = "admin_notes", columnDefinition = "text")
    private String adminNotes;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by")
    private User cancelledBy;

    public Appointment(User createdBy, AppointmentStatus status, LocalDateTime startAt, String userNotes, String adminNotes, LocalDateTime cancelledAt, User cancelledBy) {
        this.id = UUID.randomUUID();
        this.createdBy = createdBy;
        this.status = status;
        this.startAt = startAt;
        this.userNotes = userNotes;
        this.adminNotes = adminNotes;
        this.cancelledAt = cancelledAt;
        this.cancelledBy = cancelledBy;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Appointment appointment)) return false;
        return Objects.equals(this.id, appointment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", createdBy=" + createdBy +
                ", status=" + status +
                ", startAt=" + startAt +
                ", userNotes='" + userNotes + '\'' +
                ", adminNotes='" + adminNotes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", cancelledAt=" + cancelledAt +
                ", cancelledBy=" + cancelledBy +
                '}';
    }

    public void cancel() {
        this.cancelledAt = LocalDateTime.now();
    }

    public void restore() {
        this.cancelledAt = null;
    }
}

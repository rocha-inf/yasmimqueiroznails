package com.rocha_inf.yasmimqueiroznails.email.entity;

import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractBaseEntity;
import com.rocha_inf.yasmimqueiroznails.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "email_verification_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EmailVerificationToken extends AbstractBaseEntity {

    @NotNull(message = "Usuário é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Length(max = 100, message = "Token deve ter no máximo 100 caracteres")
    @NotBlank(message = "Token é obrigatório")
    @Column(name = "token", nullable = false, unique = true, length = 100)
    private String token;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @NotNull(message = "Data de expiração é obrigatória")
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public EmailVerificationToken(User user, String token) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.token = token;
        addExpirationTime(15);
    }

    private void addExpirationTime(Integer minutes){
        this.expiresAt = LocalDateTime.now().plusMinutes(minutes);
    }

    public void markAsUsed(){
        this.usedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EmailVerificationToken that)) return false;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EmailVerificationToken{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", token='" + token + '\'' +
                ", usedAt=" + usedAt +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

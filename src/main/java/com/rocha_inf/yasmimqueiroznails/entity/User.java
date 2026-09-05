package com.rocha_inf.yasmimqueiroznails.entity;

import com.rocha_inf.yasmimqueiroznails.enums.Role;
import com.rocha_inf.yasmimqueiroznails.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends AbstractSoftDeleteEntity implements UserDetails {

    @Length(max = 200, message = "Apelido deve ter no máximo 200 caracteres")
    @Column(name = "nickname", length = 200)
    private String nickname;

    @NotNull(message = "Função do usuário é obrigatória")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "role", nullable = false, columnDefinition = "user_role")
    private Role role;

    @NotNull(message = "Status do usuário é obrigatório")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false, columnDefinition = "user_status")
    private UserStatus status;

    @NotBlank(message = "Primeiro nome é obrigatório")
    @Length(max = 200, message = "Primeiro nome deve ter no máximo 200 caracteres")
    @Column(name = "first_name", length = 200, nullable = false)
    private String firstName;

    @NotBlank(message = "Último nome é obrigatório")
    @Length(max = 200, message = "Último nome deve ter no máximo 200 caracteres")
    @Column(name = "last_name", length = 200, nullable = false)
    private String lastName;

    @NotBlank(message = "Número de telefone é obrigatório")
    @Length(max = 20, min = 10, message = "Número de telefone deve ter entre 10 e 20 caracteres")
    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Email é obrigatório")
    @Length(max = 300, message = "Email deve ter no máximo 300 caracteres")
    @Email(message = "Email inválido")
    @Column(name = "email", length = 300, nullable = false)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Length(max = 200, message = "Hash da senha deve ter no máximo 200 caracteres")
    @Column(name = "password_hash", length = 200, nullable = false)
    private String passwordHash;

    @NotNull(message = "Status de bloqueio de agendamento é obrigatório")
    @Column(name = "booking_blocked", nullable = false) // default false
    private Boolean bookingBlocked;

    public User(String firstName, String lastName, String phoneNumber, String email, String passwordHash) {
        this.id = UUID.randomUUID();
        this.role = Role.CLIENT;
        this.status = UserStatus.PENDING_VERIFICATION;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.passwordHash = passwordHash;
        this.bookingBlocked = false;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(this.id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", bookingBlocked=" + bookingBlocked +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}

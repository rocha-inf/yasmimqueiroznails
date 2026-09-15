package com.rocha_inf.yasmimqueiroznails.scheduling.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "day_of_week")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DayOfWeek {

    @Id
    private Short value;

    @NotBlank(message = "Nome é obrigatório")
    @Length(max = 10, message = "Nome deve ter no máximo 10 caracteres")
    @Column(name = "name", length = 10, nullable = false, unique = true)
    protected String name;

    public DayOfWeek(Short value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DayOfWeek dayOfWeek)) return false;
        return Objects.equals(name, dayOfWeek.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "DayOfWeek{" +
                "value=" + value +
                ", name='" + name + '\'' +
                '}';
    }
}

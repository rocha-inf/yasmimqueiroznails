package com.rocha_inf.yasmimqueiroznails.scheduling.entity;

import com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractSoftDeleteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class AbstractBlockEntity extends AbstractSoftDeleteEntity {

    @NotBlank(message = "Nome é obrigatório")
    @Length(max = 200, message = "Nome deve ter no máximo 200 caracteres")
    @Column(name = "name", length = 200, nullable = false)
    protected String name;

    @Column(name = "description", columnDefinition = "TEXT")
    protected String description;


}

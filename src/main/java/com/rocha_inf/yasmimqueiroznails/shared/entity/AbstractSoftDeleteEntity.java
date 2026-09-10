package com.rocha_inf.yasmimqueiroznails.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractSoftDeleteEntity extends com.rocha_inf.yasmimqueiroznails.shared.entity.AbstractBaseEntity {

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedAt = null;
    }

}

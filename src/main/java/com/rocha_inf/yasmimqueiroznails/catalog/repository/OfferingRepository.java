package com.rocha_inf.yasmimqueiroznails.catalog.repository;

import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, UUID> {
    Optional<Offering> findByIdAndDeletedAtIsNull(UUID id);
    Page<Offering> findAllByDeletedAtIsNull(Pageable pageable);
    boolean existsByNameAndDeletedAtIsNullAndIdNot(String name, UUID id);
    boolean existsByNameAndDeletedAtIsNull(String name);
}

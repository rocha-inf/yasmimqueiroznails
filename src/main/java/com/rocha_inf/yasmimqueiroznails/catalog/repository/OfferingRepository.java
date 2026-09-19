package com.rocha_inf.yasmimqueiroznails.catalog.repository;

import com.rocha_inf.yasmimqueiroznails.catalog.entity.Offering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfferingRepository extends JpaRepository<Offering, UUID> {
}

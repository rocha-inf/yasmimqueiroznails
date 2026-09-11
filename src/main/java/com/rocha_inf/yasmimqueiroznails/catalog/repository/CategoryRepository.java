package com.rocha_inf.yasmimqueiroznails.catalog.repository;

import com.rocha_inf.yasmimqueiroznails.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByNameAndDeletedAtIsNull(String name);

}

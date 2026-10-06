package com.rocha_inf.yasmimqueiroznails.block.repository;

import com.rocha_inf.yasmimqueiroznails.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface BlockRepository extends JpaRepository<Block, UUID> {

    @Query("""
        SELECT COUNT(b) > 0
        FROM Block b
        WHERE b.startsAt < :endsAt AND b.endsAt > :startsAt AND b.deletedAt IS NULL
    """)
    boolean existsOverLapping(LocalDateTime startsAt, LocalDateTime endsAt);

}

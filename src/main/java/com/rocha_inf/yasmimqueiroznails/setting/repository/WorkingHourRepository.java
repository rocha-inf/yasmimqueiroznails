package com.rocha_inf.yasmimqueiroznails.setting.repository;

import com.rocha_inf.yasmimqueiroznails.setting.entity.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, UUID> {

    @Query("""
        SELECT COUNT(w) > 0
        FROM WorkingHour w
        WHERE w.dayOfWeek = :dayOfWeek AND w.startsAt < :endsAt AND w.endsAt > :startsAt
    """)
    boolean existOverLapping(DayOfWeek dayOfWeek, LocalTime startsAt, LocalTime endsAt);

}
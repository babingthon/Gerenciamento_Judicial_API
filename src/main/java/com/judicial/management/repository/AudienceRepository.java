package com.judicial.management.repository;

import com.judicial.management.model.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AudienceRepository extends JpaRepository<Audience, Long> {
    @Query("SELECT a FROM Audience a JOIN a.legalProcess p WHERE p.court = :court AND a.location = :location AND a.dateTime = :dateTime")
    Optional<Audience> findAudience(@Param("court") String court, @Param("location") String location, @Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT a FROM Audience a JOIN a.legalProcess p WHERE p.district = :district AND CAST(a.dateTime AS date) = :day")
    List<Audience> findByDistrictAndDay(@Param("district") String district, @Param("day") LocalDate day);
}

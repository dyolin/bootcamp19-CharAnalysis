package com.example.day2_simulasiSpringboot.repository;

import com.example.day2_simulasiSpringboot.entity.CharAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//<T, ID> id CharAnalysis tipe datanya Long
public interface CharAnalysisRepository extends JpaRepository<CharAnalysis, Long> {
    List<CharAnalysis> findByKalimatContainingIgnoreCase(String kata);

    @Query("SELECT c FROM CharAnalysis c WHERE c.totalHuruf >= :min")
    List<CharAnalysis> findByMinimal (@Param("min") int min);

}

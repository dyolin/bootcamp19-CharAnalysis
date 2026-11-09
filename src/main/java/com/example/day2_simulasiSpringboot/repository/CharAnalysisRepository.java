package com.example.day2_simulasiSpringboot.repository;

import com.example.day2_simulasiSpringboot.entity.CharAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//<T, ID> id CharAnalysis tipe datanya Long
public interface CharAnalysisRepository extends JpaRepository<CharAnalysis, Long> {
    List<CharAnalysis> findByKalimatContainingIgnoreCase(String kata);

}

package com.example.day2_simulasiSpringboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "mst_char_analysis")
@Data
public class CharAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String kalimat;

    @Column(nullable = true, length = 100)
    private String author;

    @Column(name = "total_huruf")
    private int totalHuruf;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

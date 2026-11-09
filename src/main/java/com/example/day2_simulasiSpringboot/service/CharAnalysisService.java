package com.example.day2_simulasiSpringboot.service;

import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;

import java.util.List;

public interface CharAnalysisService {
    List<CharAnalysisResponse> cari(String kata);
    CharAnalysisResponse insert (String kata);
}

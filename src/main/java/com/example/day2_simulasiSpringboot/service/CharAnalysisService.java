package com.example.day2_simulasiSpringboot.service;

import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;

import java.util.List;

public interface CharAnalysisService {
    //service untuk db
    List<CharAnalysisResponse> cari(String kata);
    CharAnalysisResponse insert (String kata);
    CharAnalysisResponse getById (Long id);
    List<CharAnalysisResponse> findByMinimal (int min);
    List<CharAnalysisResponse> findLatestFive();

    //service untuk third party api
    CharAnalysisResponse analisisDariQuote();
}

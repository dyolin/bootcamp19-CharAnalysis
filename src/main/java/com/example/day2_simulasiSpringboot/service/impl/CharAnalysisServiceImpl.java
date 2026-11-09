package com.example.day2_simulasiSpringboot.service.impl;

import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;
import com.example.day2_simulasiSpringboot.entity.CharAnalysis;
import com.example.day2_simulasiSpringboot.repository.CharAnalysisRepository;
import com.example.day2_simulasiSpringboot.service.CharAnalysisService;
import com.example.day2_simulasiSpringboot.util.CharCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__( @Autowired))
public class CharAnalysisServiceImpl implements CharAnalysisService {

    private final CharAnalysisRepository charAnalysisRepository;

    @Override
    public List<CharAnalysisResponse> cari(String kata) {
        List<CharAnalysis> semua;
        if(kata == null || kata.isBlank()){
            semua = charAnalysisRepository.findAll(); //bisa langsung panggil karena di respository sudah extends JpaRepository
        }else {
            semua = charAnalysisRepository.findByKalimatContainingIgnoreCase(kata);
        }
        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for(CharAnalysis data : semua){
            hasil.add(toResponse(data));
        }
        return hasil;
    }

    @Override
    public CharAnalysisResponse insert (String kata){
        CharCounter counter = new CharCounter(kata);
        CharAnalysis data = new CharAnalysis();

        data.setKalimat(kata);
        data.setTotalHuruf(counter.getTotalHuruf());
        data.setCreatedAt(LocalDateTime.now());

        CharAnalysis saved = charAnalysisRepository.save(data);

        return toResponse(saved);
    }

    @Override
    public CharAnalysisResponse getById (Long id) {
        CharAnalysis data = charAnalysisRepository.findById(id).orElse(null);

        if (data != null) {
            return toResponse(data);
        } else {
            return null;
        }
    }

    @Override
    public List<CharAnalysisResponse> findByMinimal(int min) {
        List<CharAnalysis> data = charAnalysisRepository.findByMinimal(min);

        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for(CharAnalysis d : data){
            hasil.add(toResponse(d));
        }
        return hasil;
    }

    private static CharAnalysisResponse toResponse(CharAnalysis data){
        CharCounter counter = new CharCounter(data.getKalimat());
        return CharAnalysisResponse.from(data, counter.toMap());
    }
}

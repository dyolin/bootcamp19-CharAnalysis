package com.example.day2_simulasiSpringboot.service.impl;

import com.example.day2_simulasiSpringboot.dto.feign.QuoteResponse;
import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;
import com.example.day2_simulasiSpringboot.entity.CharAnalysis;
import com.example.day2_simulasiSpringboot.exception.AnalysisNotFoundException;
import com.example.day2_simulasiSpringboot.exception.DataAlreadyExistException;
import com.example.day2_simulasiSpringboot.exception.QuoteUnavailableException;
import com.example.day2_simulasiSpringboot.repository.CharAnalysisRepository;
import com.example.day2_simulasiSpringboot.rest.QuoteClient;
import com.example.day2_simulasiSpringboot.service.CharAnalysisService;
import com.example.day2_simulasiSpringboot.util.CharCounter;
import feign.FeignException;
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
    private final QuoteClient client; //perlu nambahin @EnablerFeignClient di main

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
        //List<CharAnalysis> existedData = charAnalysisRepository.findByKalimatContainingIgnoreCase(kata);
        boolean isExist = charAnalysisRepository.existsByKalimatIgnoreCase(kata);

        if(isExist){
            throw new DataAlreadyExistException(kata);
        }

//        CharCounter counter = new CharCounter(kata);
//        CharAnalysis data = new CharAnalysis();
//
//        data.setKalimat(kata);
//        data.setTotalHuruf(counter.getTotalHuruf());
//        data.setCreatedAt(LocalDateTime.now());
//
//        CharAnalysis saved = charAnalysisRepository.save(data);

        return simpan(kata, null);
    }

    @Override
    public CharAnalysisResponse getById (Long id) {
        CharAnalysis data = charAnalysisRepository.findById(id)
                .orElseThrow(() -> new AnalysisNotFoundException(id));

        return toResponse(data);
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

    @Override
    public List<CharAnalysisResponse> findLatestFive() {
        List<CharAnalysis> data = charAnalysisRepository.findLatestFive();

        List<CharAnalysisResponse> hasil = new ArrayList<>();
        for (CharAnalysis d : data){
            hasil.add(toResponse(d));
        }

        return hasil;
    }

    @Override
    public CharAnalysisResponse analisisDariQuote() {
        QuoteResponse quote;
        try{
            quote = client.getQuote();
        }catch (FeignException feignException){
            throw new QuoteUnavailableException("Service unavailable");
        }
        return simpan(quote.getQuote(), quote.getAuthor());
    }

    private static CharAnalysisResponse toResponse(CharAnalysis data){
        CharCounter counter = new CharCounter(data.getKalimat());
        return CharAnalysisResponse.from(data, counter.toMap());
    }

    public CharAnalysisResponse simpan(String kalimat, String author){
        CharCounter counter = new CharCounter(kalimat);
        CharAnalysis data = new CharAnalysis();

        data.setKalimat(kalimat);
        data.setAuthor(author);
        data.setTotalHuruf(counter.getTotalHuruf());
        data.setCreatedAt(LocalDateTime.now());
        charAnalysisRepository.save(data);

        return CharAnalysisResponse.from(data, counter.toMap());
    }

}

package com.example.day2_simulasiSpringboot.controller;

import com.example.day2_simulasiSpringboot.dto.request.CharAnalysisRequest;
import com.example.day2_simulasiSpringboot.dto.response.BaseResponse;
import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;
import com.example.day2_simulasiSpringboot.entity.CharAnalysis;
import com.example.day2_simulasiSpringboot.service.CharAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzerController {

    private final CharAnalysisService charAnalysisService;

    @GetMapping
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat){
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }

    @PostMapping
    public BaseResponse<CharAnalysisResponse> insert(
            @RequestBody CharAnalysisRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId
    ){
        if (request.getKalimat() == null || request.getKalimat().isBlank()) {
            return BaseResponse.error("Kalimat tidak boleh kosong");
        }

        System.out.println("X-Request-Id: " +requestId+ ", kalimat: " +request.getKalimat());
        return BaseResponse.ok(charAnalysisService.insert(request.getKalimat()));
    }

    @GetMapping("/{id}")
    public BaseResponse<CharAnalysisResponse> getById (@PathVariable Long id){
        CharAnalysisResponse result = charAnalysisService.getById(id);

        if(result != null){
            return BaseResponse.ok(result);
        }else{
            return BaseResponse.error("Data dengan id " +id+ " tidak ditemukan");
        }
    }

    @GetMapping("/minimal")
    public BaseResponse<List<CharAnalysisResponse>> findByMinimal (@RequestParam int min){
        List<CharAnalysisResponse> result = charAnalysisService.findByMinimal(min);

        if(result.isEmpty()){
            return BaseResponse.error("Tidak ada kalimat dengan total huruf nya >= " +min);
        }

        return BaseResponse.ok(result);
    }

    @GetMapping("/terbaru")
    public BaseResponse<List<CharAnalysisResponse>> findLatestFive(){
        List<CharAnalysisResponse> result = charAnalysisService.findLatestFive();

        if (result.isEmpty()){
            return BaseResponse.error("Data is empty");
        }
        return BaseResponse.ok(result);
    }
}

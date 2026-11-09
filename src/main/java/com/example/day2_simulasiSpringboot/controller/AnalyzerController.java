package com.example.day2_simulasiSpringboot.controller;

import com.example.day2_simulasiSpringboot.dto.request.CharAnalysisRequest;
import com.example.day2_simulasiSpringboot.dto.response.BaseResponse;
import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;
import com.example.day2_simulasiSpringboot.service.CharAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AnalyzerController {

    private final CharAnalysisService charAnalysisService;

    @GetMapping("/find")
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat){
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }

    @PostMapping
    public BaseResponse<CharAnalysisResponse> insert(@RequestBody CharAnalysisRequest request){
        return BaseResponse.ok(charAnalysisService.insert(request.getKalimat()));
    }

    //method post untuk insert kalimat
    //pakai error handler nya
    //validasi request body
    //ga usah bikin nama endpoint
}

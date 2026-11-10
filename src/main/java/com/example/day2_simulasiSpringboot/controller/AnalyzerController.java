package com.example.day2_simulasiSpringboot.controller;

import com.example.day2_simulasiSpringboot.dto.request.CharAnalysisRequest;
import com.example.day2_simulasiSpringboot.dto.response.BaseResponse;
import com.example.day2_simulasiSpringboot.dto.response.CharAnalysisResponse;
import com.example.day2_simulasiSpringboot.service.CharAnalysisService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AnalyzerController {

    private final CharAnalysisService charAnalysisService;

    @GetMapping
    public BaseResponse<List<CharAnalysisResponse>> cari(@RequestParam(required = false) String kalimat){
        return BaseResponse.ok(charAnalysisService.cari(kalimat));
    }

    @PostMapping
    public BaseResponse<CharAnalysisResponse> insert(
            @Valid @RequestBody CharAnalysisRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId
    ){

        //System.out.println("X-Request-Id: " +requestId+ ", kalimat: " +request.getKalimat());
        log.info("X-Request-Id: {}, kalimat: {}", requestId,  request.getKalimat());
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

    @PostMapping("/from-quote")
    public BaseResponse<CharAnalysisResponse> fromQuote(){
        return BaseResponse.ok(charAnalysisService.analisisDariQuote());
    }

    @PostMapping("/from-quote/batch/{jumlah}")
    public BaseResponse<List<CharAnalysisResponse>> fromQuoteBatch(
            @PathVariable
            @Min(value = 1, message = "Jumlah minimal 1")
            @Max(value = 10, message = "Jumlah maksimal 10")
            int jumlah){

        return BaseResponse.ok(charAnalysisService.analisisBatchQuote(jumlah));
    }

}

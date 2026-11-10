package com.example.day2_simulasiSpringboot.dto.feign;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) //ignore kalau ada json property dari thirdAPI tapi ngga ada fieldnya di class
public class QuoteResponse {
    private Long id;
    private String quote;
    private String author;
}

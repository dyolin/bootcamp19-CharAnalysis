package com.example.day2_simulasiSpringboot.rest;

import com.example.day2_simulasiSpringboot.dto.feign.QuoteResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "quoteClient", url = "${quote.api.url}")
public interface QuoteClient {

    @GetMapping("/quotes/random")
    QuoteResponse getQuote();

    @GetMapping("/quotes/random/{length}")
    List<QuoteResponse> getRandomQuotes(@PathVariable("length") int length);
}

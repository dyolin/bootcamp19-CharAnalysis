package com.example.day2_simulasiSpringboot.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Valid
public class CharAnalysisRequest {
    @NotBlank(message = "Kalimat wajib isi")
    @Size(max = 255, message = "Kalimat wajib isi sampai 255 karakter")
    private String kalimat;
}

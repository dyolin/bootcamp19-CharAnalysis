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
    @Size(max = 500, message = "Maksimal panjang kalimat adalah 500 karakter")
    private String kalimat;
}

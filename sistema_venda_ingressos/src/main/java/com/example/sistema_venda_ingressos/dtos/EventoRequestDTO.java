package com.example.sistema_venda_ingressos.dtos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EventoRequestDTO(
    @NotBlank String nome,
    @NotBlank String local,
    @NotNull LocalDateTime dataEvento,
    @NotEmpty List<IngressoRequestDTO> ingressos

){}

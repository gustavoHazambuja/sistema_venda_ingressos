package com.example.sistema_venda_ingressos.dtos;

import jakarta.validation.constraints.NotBlank;

public record EventoRequestDTO(
    @NotBlank String nome,
    @NotBlank String local

){}

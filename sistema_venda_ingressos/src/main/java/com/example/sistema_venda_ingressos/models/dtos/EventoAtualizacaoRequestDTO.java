package com.example.sistema_venda_ingressos.models.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoAtualizacaoRequestDTO(
    @NotBlank String nome,
    @NotBlank String local,
    @NotNull LocalDateTime dataEvento
) {}
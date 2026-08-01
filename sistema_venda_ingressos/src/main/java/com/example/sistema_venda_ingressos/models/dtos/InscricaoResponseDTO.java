package com.example.sistema_venda_ingressos.models.dtos;

import java.time.LocalDateTime;

import com.example.sistema_venda_ingressos.models.StatusInscricao;

public record InscricaoResponseDTO(
    Long id,
    String nomeUsuario,
    String nomeEvento,
    LocalDateTime dataInscricao,
    StatusInscricao status
) {
    
}

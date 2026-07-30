package com.example.sistema_venda_ingressos.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record EventoResponseDTO(
    Long id,
    String nome,
    String local,
    LocalDateTime dataEvento,
    List<IngressoResponseDTO> ingressos
) {
    
}

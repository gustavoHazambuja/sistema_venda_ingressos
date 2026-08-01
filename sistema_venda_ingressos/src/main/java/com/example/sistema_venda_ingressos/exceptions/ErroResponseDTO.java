package com.example.sistema_venda_ingressos.exceptions;

import java.time.LocalDateTime;

public record ErroResponseDTO(
    LocalDateTime timestamp,
    int status,
    String erro,
    String mensagem
) {}
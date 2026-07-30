package com.example.sistema_venda_ingressos.dtos;

import jakarta.validation.constraints.NotNull;

public record InscricaoRequestDTO (
    
    @NotNull Long usuarioId,
    @NotNull Long ingressoId

){}

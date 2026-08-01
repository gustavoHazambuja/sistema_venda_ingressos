package com.example.sistema_venda_ingressos.models.dtos;

import java.math.BigDecimal;

import com.example.sistema_venda_ingressos.models.TipoIngresso;

import jakarta.validation.constraints.NotNull;

public record IngressoRequestDTO (
    
    @NotNull BigDecimal preco,
    @NotNull Integer quantidadeTotal,
    @NotNull TipoIngresso tipo
){}

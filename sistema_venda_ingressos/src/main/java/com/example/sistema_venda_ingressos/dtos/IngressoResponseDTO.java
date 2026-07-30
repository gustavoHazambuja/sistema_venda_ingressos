package com.example.sistema_venda_ingressos.dtos;

import java.math.BigDecimal;

import com.example.sistema_venda_ingressos.models.TipoIngresso;

public record IngressoResponseDTO(
    Long id, BigDecimal preco, 
    Integer quantidadeTotal,
    Integer quantidadeDisponivel,
    TipoIngresso tipo) {}

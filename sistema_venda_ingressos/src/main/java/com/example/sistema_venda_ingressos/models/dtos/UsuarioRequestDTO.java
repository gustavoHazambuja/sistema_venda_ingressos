package com.example.sistema_venda_ingressos.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO (
    
    @NotBlank String nome,
    @NotBlank String email,
    @NotBlank String senha
){}




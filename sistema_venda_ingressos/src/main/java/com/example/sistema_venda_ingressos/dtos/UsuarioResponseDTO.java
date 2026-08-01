package com.example.sistema_venda_ingressos.dtos;

import com.example.sistema_venda_ingressos.models.UsuarioModel;

public record UsuarioResponseDTO(Long id, String nome, String email) {


    public static UsuarioResponseDTO fromModel(UsuarioModel usuario){
        return new UsuarioResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
}

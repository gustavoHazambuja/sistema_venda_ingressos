package com.example.sistema_venda_ingressos.exceptions;

public class RegraDeNegocioException extends RuntimeException {
    
    public RegraDeNegocioException(String mensagem){
        super(mensagem);
    }
}

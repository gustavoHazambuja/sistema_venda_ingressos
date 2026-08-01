package com.example.sistema_venda_ingressos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 -- recurso que não existe (usuário, evento, ingresso, inscrição)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponseDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {

        ErroResponseDTO erro = new ErroResponseDTO(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Recurso não encontrado",
            ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // 400 -- violação de regra de negócio (email duplicado, estoque esgotado, etc.)
    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErroResponseDTO> handleRegraDeNegocio(RegraDeNegocioException ex) {

        ErroResponseDTO erro = new ErroResponseDTO(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Regra de negócio violada",
            ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // 400 -- falha de validação do @Valid (campos @NotBlank, @NotNull, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidacao(MethodArgumentNotValidException ex) {

        Map<String, String> erros = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(erro ->
            erros.put(erro.getField(), erro.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
    }

    // 500 -- qualquer exceção não prevista; rede de segurança final
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> handleGenerica(Exception ex) {

        ErroResponseDTO erro = new ErroResponseDTO(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno",
            "Ocorreu um erro inesperado. Tente novamente mais tarde."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
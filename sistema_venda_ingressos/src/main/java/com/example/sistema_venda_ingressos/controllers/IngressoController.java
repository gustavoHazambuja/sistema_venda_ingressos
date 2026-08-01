package com.example.sistema_venda_ingressos.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistema_venda_ingressos.models.dtos.IngressoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.IngressoResponseDTO;
import com.example.sistema_venda_ingressos.services.IngressoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos/{eventoId}/ingressos")
public class IngressoController {

    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @PostMapping
    public ResponseEntity<IngressoResponseDTO> criar(
            @PathVariable Long eventoId,
            @RequestBody @Valid IngressoRequestDTO dto) {
        IngressoResponseDTO response = ingressoService.criarIngresso(eventoId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<IngressoResponseDTO>> listarPorEvento(
            @PathVariable Long eventoId,
            Pageable pageable) {
        return ResponseEntity.ok(ingressoService.listarPorEvento(eventoId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngressoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ingressoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngressoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid IngressoRequestDTO dto) {
        return ResponseEntity.ok(ingressoService.atualizarIngresso(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ingressoService.deletarIngresso(id);
        return ResponseEntity.noContent().build();
    }
}
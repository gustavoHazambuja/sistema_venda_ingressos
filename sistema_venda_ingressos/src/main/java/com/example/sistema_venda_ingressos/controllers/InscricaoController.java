package com.example.sistema_venda_ingressos.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistema_venda_ingressos.models.dtos.InscricaoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.InscricaoResponseDTO;
import com.example.sistema_venda_ingressos.services.InscricaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping
    public ResponseEntity<InscricaoResponseDTO> inscrever(@RequestBody @Valid InscricaoRequestDTO dto) {
        InscricaoResponseDTO response = inscricaoService.inscrever(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscricaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inscricaoService.buscarPorId(id));
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<Page<InscricaoResponseDTO>> listarPorUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable) {
        return ResponseEntity.ok(inscricaoService.listarPorUsuario(usuarioId, pageable));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<InscricaoResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(inscricaoService.cancelar(id));
    }
}
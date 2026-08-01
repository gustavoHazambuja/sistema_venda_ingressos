package com.example.sistema_venda_ingressos.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.sistema_venda_ingressos.models.dtos.EventoAtualizacaoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.EventoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.EventoResponseDTO;
import com.example.sistema_venda_ingressos.services.EventoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @PostMapping
    public ResponseEntity<EventoResponseDTO> criar(@RequestBody @Valid EventoRequestDTO dto) {
        EventoResponseDTO response = eventoService.criarEvento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<EventoResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(eventoService.listarEventos(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid EventoAtualizacaoRequestDTO dto) {
        return ResponseEntity.ok(eventoService.atualizarEvento(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletarEvento(id);
        return ResponseEntity.noContent().build();
    }
}
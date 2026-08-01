package com.example.sistema_venda_ingressos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sistema_venda_ingressos.dtos.IngressoRequestDTO;
import com.example.sistema_venda_ingressos.dtos.IngressoResponseDTO;
import com.example.sistema_venda_ingressos.exceptions.RegraDeNegocioException;
import com.example.sistema_venda_ingressos.models.EventoModel;
import com.example.sistema_venda_ingressos.models.IngressoModel;
import com.example.sistema_venda_ingressos.repositories.EventoRepository;
import com.example.sistema_venda_ingressos.repositories.IngressoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class IngressoService {

    private final IngressoRepository ingressoRepository;
    private final EventoRepository eventoRepository;

    public static final String MENSAGEM_ERRO = "Ingresso não encontrado com o id ";

    public IngressoService(IngressoRepository ingressoRepository, EventoRepository eventoRepository) {
        this.ingressoRepository = ingressoRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public IngressoResponseDTO criarIngresso(Long eventoId, IngressoRequestDTO dto) {

        EventoModel evento = eventoRepository.findById(eventoId)
            .orElseThrow(() -> new EntityNotFoundException("Evento não encontrado com o id " + eventoId));

        boolean jaExiste = ingressoRepository.findByEventoIdAndTipo(eventoId, dto.tipo()).isPresent();
        if (jaExiste) {
            throw new RegraDeNegocioException(
                "Já existe um lote do tipo " + dto.tipo() + " para este evento");
        }

        IngressoModel ingresso = new IngressoModel(
            dto.preco(),
            dto.quantidadeTotal(),
            dto.quantidadeTotal(), 
            dto.tipo(),
            evento
        );

        ingressoRepository.save(ingresso);

        return toResponseDTO(ingresso);
    }

    public IngressoResponseDTO buscarPorId(Long id) {

        IngressoModel ingresso = ingressoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(MENSAGEM_ERRO + id));

        return toResponseDTO(ingresso);
    }

    public Page<IngressoResponseDTO> listarPorEvento(Long eventoId, Pageable pageable) {
        return ingressoRepository.findByEventoId(eventoId, pageable)
            .map(this::toResponseDTO);
    }

    @Transactional
    public IngressoResponseDTO atualizarIngresso(Long id, IngressoRequestDTO dto) {

        IngressoModel ingresso = ingressoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(MENSAGEM_ERRO + id));

        ingresso.setPreco(dto.preco());

        ingressoRepository.save(ingresso);

        return toResponseDTO(ingresso);
    }

    public void deletarIngresso(Long id) {

        IngressoModel ingresso = ingressoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(MENSAGEM_ERRO + id));

        ingressoRepository.delete(ingresso);
    }

    private IngressoResponseDTO toResponseDTO(IngressoModel ingresso) {
        return new IngressoResponseDTO(
            ingresso.getId(),
            ingresso.getPreco(),
            ingresso.getQuantidadeTotal(),
            ingresso.getQuantidadeDisponivel(),
            ingresso.getTipo()
        );
    }
}
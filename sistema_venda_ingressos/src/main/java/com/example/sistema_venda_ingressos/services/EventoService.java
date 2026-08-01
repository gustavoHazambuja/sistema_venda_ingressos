package com.example.sistema_venda_ingressos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sistema_venda_ingressos.exceptions.RecursoNaoEncontradoException;
import com.example.sistema_venda_ingressos.models.EventoModel;
import com.example.sistema_venda_ingressos.models.dtos.EventoAtualizacaoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.EventoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.EventoResponseDTO;
import com.example.sistema_venda_ingressos.models.dtos.IngressoResponseDTO;
import com.example.sistema_venda_ingressos.repositories.EventoRepository;

@Service
public class EventoService {

    public static final String MENSAGEM_ERRO = "Evento não encontrado com o id ";
    
    private final EventoRepository eventoRepository;
    private final IngressoService ingressoService;

    public EventoService(EventoRepository eventoRepository, IngressoService ingressoService) {
        this.eventoRepository = eventoRepository;
        this.ingressoService = ingressoService;
    }

    @Transactional
    public EventoResponseDTO criarEvento(EventoRequestDTO dto){

        EventoModel evento = new EventoModel(dto.nome(), dto.local(), dto.dataEvento());
        eventoRepository.save(evento);

        List<IngressoResponseDTO> ingressosDTO = dto.ingressos().stream()
            .map(ingressoDTO -> ingressoService.criarIngresso(evento.getId(), ingressoDTO))
            .toList();  

        return new EventoResponseDTO(
            evento.getId(),
            evento.getNome(),
            evento.getLocal(),
            evento.getDataEvento(),
            ingressosDTO
        );
    }

    public EventoResponseDTO buscarPorId(Long id) {

        EventoModel evento = eventoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        return toResponseDTO(evento);
    }

    public Page<EventoResponseDTO> listarEventos(Pageable pageable){

        return eventoRepository.findAll(pageable)
            .map(this::toResponseDTO);
    }

    @Transactional
    public EventoResponseDTO atualizarEvento(Long id, EventoAtualizacaoRequestDTO novoEvento) {

        EventoModel evento = eventoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        evento.setNome(novoEvento.nome());
        evento.setLocal(novoEvento.local());
        evento.setDataEvento(novoEvento.dataEvento());

        eventoRepository.save(evento);

        return toResponseDTO(evento);
    }

    public void deletarEvento(Long id){

        EventoModel evento = eventoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        eventoRepository.delete(evento);
    }





    private EventoResponseDTO toResponseDTO(EventoModel evento) {
        List<IngressoResponseDTO> ingressosDTO = evento.getIngressos().stream()
            .map(ing -> new IngressoResponseDTO(
                ing.getId(), ing.getPreco(), ing.getQuantidadeTotal(),
                ing.getQuantidadeDisponivel(), ing.getTipo()))
            .toList();

        return new EventoResponseDTO(
            evento.getId(), evento.getNome(), evento.getLocal(),
            evento.getDataEvento(), ingressosDTO
        );
    }
}

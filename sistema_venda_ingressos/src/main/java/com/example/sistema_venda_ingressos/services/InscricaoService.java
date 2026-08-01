package com.example.sistema_venda_ingressos.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.sistema_venda_ingressos.exceptions.RecursoNaoEncontradoException;
import com.example.sistema_venda_ingressos.exceptions.RegraDeNegocioException;
import com.example.sistema_venda_ingressos.models.IngressoModel;
import com.example.sistema_venda_ingressos.models.InscricaoModel;
import com.example.sistema_venda_ingressos.models.StatusInscricao;
import com.example.sistema_venda_ingressos.models.UsuarioModel;
import com.example.sistema_venda_ingressos.models.dtos.InscricaoRequestDTO;
import com.example.sistema_venda_ingressos.models.dtos.InscricaoResponseDTO;
import com.example.sistema_venda_ingressos.repositories.IngressoRepository;
import com.example.sistema_venda_ingressos.repositories.InscricaoRepository;
import com.example.sistema_venda_ingressos.repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class InscricaoService {
    

    private final InscricaoRepository inscricaoRepository;
    private final IngressoRepository ingressoRepository;
    private final UsuarioRepository usuarioRepository;

    public static final String MENSAGEM_ERRO = "Inscrição não encontrada com o id ";

    public InscricaoService(InscricaoRepository inscricaoRepository,
                            IngressoRepository ingressoRepository,
                            UsuarioRepository usuarioRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.ingressoRepository = ingressoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    @Transactional
    public InscricaoResponseDTO inscrever(InscricaoRequestDTO dto) {

        UsuarioModel usuario = usuarioRepository.findById(dto.usuarioId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o id " + dto.usuarioId()));

        IngressoModel ingresso = ingressoRepository.findById(dto.ingressoId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Ingresso não encontrado com o id " + dto.ingressoId()));

        // regra 1: não pode se inscrever duas vezes no mesmo evento
        boolean jaInscrito = inscricaoRepository
            .existsByUsuarioIdAndIngressoEventoId(dto.usuarioId(), ingresso.getEvento().getId());

        if (jaInscrito) {
            throw new RegraDeNegocioException("Usuário já inscrito neste evento");
        }

        // regra 2: precisa ter ingresso disponível
        if (ingresso.getQuantidadeDisponivel() <= 0) {
            throw new RegraDeNegocioException("Ingressos esgotados para este tipo: " + ingresso.getTipo());
        }

        // regra 3: decrementa o estoque -- acesso direto ao IngressoRepository
        ingresso.setQuantidadeDisponivel(ingresso.getQuantidadeDisponivel() - 1);
        ingressoRepository.save(ingresso);

        InscricaoModel inscricao = new InscricaoModel(
            usuario,
            ingresso,
            LocalDateTime.now(),
            StatusInscricao.CONFIRMADA
        );

        inscricaoRepository.save(inscricao);

        return toResponseDTO(inscricao);
    }


    @Transactional
    public InscricaoResponseDTO cancelar(Long id) {

        InscricaoModel inscricao = inscricaoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        if (inscricao.getStatus() == StatusInscricao.CANCELADA) {
            throw new RegraDeNegocioException("Esta inscrição já está cancelada");
        }

        inscricao.setStatus(StatusInscricao.CANCELADA);
        inscricaoRepository.save(inscricao);

        // devolve a unidade ao estoque
        IngressoModel ingresso = inscricao.getIngresso();
        ingresso.setQuantidadeDisponivel(ingresso.getQuantidadeDisponivel() + 1);
        ingressoRepository.save(ingresso);

        return toResponseDTO(inscricao);
    }


    public InscricaoResponseDTO buscarPorId(Long id) {

        InscricaoModel inscricao = inscricaoRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException(MENSAGEM_ERRO + id));

        return toResponseDTO(inscricao);
    }

    public Page<InscricaoResponseDTO> listarPorUsuario(Long usuarioId, Pageable pageable) {

        return inscricaoRepository.findByUsuarioId(usuarioId, pageable)
            .map(this::toResponseDTO);
    }


    
     private InscricaoResponseDTO toResponseDTO(InscricaoModel inscricao) {
        return new InscricaoResponseDTO(
            inscricao.getId(),
            inscricao.getUsuario().getNome(),
            inscricao.getIngresso().getEvento().getNome(),
            inscricao.getDataInscricao(),
            inscricao.getStatus()
        );
    }


   
}

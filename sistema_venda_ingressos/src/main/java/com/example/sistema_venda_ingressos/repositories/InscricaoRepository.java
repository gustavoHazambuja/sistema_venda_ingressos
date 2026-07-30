package com.example.sistema_venda_ingressos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistema_venda_ingressos.models.InscricaoModel;

public interface InscricaoRepository extends JpaRepository<InscricaoModel, Long> {

    boolean existsByUsuarioIdAndIngressoEventoId(Long usuarioId, Long eventoId);

    Page<InscricaoModel> findByUsuarioId(Long usuarioId, Pageable pageable);
    
}

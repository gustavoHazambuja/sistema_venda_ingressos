package com.example.sistema_venda_ingressos.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistema_venda_ingressos.models.IngressoModel;
import com.example.sistema_venda_ingressos.models.TipoIngresso;

public interface IngressoRepository extends JpaRepository<IngressoModel, Long> {

    Optional<IngressoModel> findByEventoIdAndTipo(Long eventoID, TipoIngresso tipo);

    Page<IngressoModel> findByEventoId(Long eventoId, Pageable pageable);
    
}

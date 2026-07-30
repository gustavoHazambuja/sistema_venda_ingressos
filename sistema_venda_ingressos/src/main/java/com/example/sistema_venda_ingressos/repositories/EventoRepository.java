package com.example.sistema_venda_ingressos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sistema_venda_ingressos.models.EventoModel;

public interface EventoRepository extends JpaRepository<EventoModel, Long> {

}

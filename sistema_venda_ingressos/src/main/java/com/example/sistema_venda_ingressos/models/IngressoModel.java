package com.example.sistema_venda_ingressos.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_ingressos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IngressoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal preco;
    private Integer quantidadeTotal;
    private Integer quantidadeDisponivel;

    @Enumerated(EnumType.STRING)
    private TipoIngresso tipo;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private EventoModel evento;
}

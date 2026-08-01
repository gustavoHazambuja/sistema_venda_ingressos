package com.example.sistema_venda_ingressos.models;

import java.time.LocalDateTime;

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
@Table(name = "tb_inscricoes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioModel usuario;

    @ManyToOne
    @JoinColumn(name = "ingresso_id")
    private IngressoModel ingresso;

    private LocalDateTime dataInscricao;

    @Enumerated(EnumType.STRING)
    private StatusInscricao status;


    public InscricaoModel(UsuarioModel usuario, IngressoModel ingresso, LocalDateTime dataInscricao, StatusInscricao status){
        this.usuario = usuario;
        this.ingresso = ingresso;
        this.dataInscricao = dataInscricao;
        this.status = status;
    }
}

package com.example.sistema_venda_ingressos.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_eventos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String local;
    private LocalDateTime dataEvento;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<IngressoModel> ingressos;
}

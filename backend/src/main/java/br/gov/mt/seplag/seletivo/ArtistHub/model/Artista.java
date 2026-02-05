package br.gov.mt.seplag.seletivo.ArtistHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "ARTISTA")
public class Artista extends Auditoria{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @Column(name = "NM_NOME")
    private String nome;
}

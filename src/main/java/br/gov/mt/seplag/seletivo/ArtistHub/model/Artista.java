package br.gov.mt.seplag.seletivo.ArtistHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "ART_ARTAISTA")
public class Artista extends Auditoria{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    private Long id;
    
    @Column(name = "NM_NOME")
    private String name;
}

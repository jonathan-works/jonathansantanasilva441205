package br.gov.mt.seplag.seletivo.ArtistHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "ART_ALBUM")
public class Album extends Auditoria{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name = "NM_TITULO")
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @Column(name = "ART_ID")
    private Artista artista;
}

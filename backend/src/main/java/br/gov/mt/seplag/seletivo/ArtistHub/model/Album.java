package br.gov.mt.seplag.seletivo.ArtistHub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "ALBUM")
public class Album extends Auditoria{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NM_TITULO")
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "ART_ID")
    private Artista artista;
}

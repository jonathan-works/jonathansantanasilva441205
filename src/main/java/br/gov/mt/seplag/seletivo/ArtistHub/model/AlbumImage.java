package br.gov.mt.seplag.seletivo.ArtistHub.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "ALBUM_IMAGEM")
public class AlbumImage extends Auditoria{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NM_IMAGEM")
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name = "ALB_ID")
    private Album album;
}

package br.gov.mt.seplag.seletivo.ArtistHub.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;

import lombok.Data;

@Data
public class Auditoria {

    @Column(name = "NM_USUARIO_CRIACAO")
    private LocalDateTime usuarioCriacao;

    @Column(name = "NM_USUARIO_ATUALIZACAO")
    private LocalDateTime usuarioAtualizacao;

    @Column(name = "DH_CRIACAO")
    private LocalDateTime dataCriacao;

    @Column(name = "DH_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;
    
}

package br.gov.mt.seplag.seletivo.ArtistHub.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditoria {

    @CreatedBy
    @Column(name = "NM_USUARIO_CRIACAO")
    private String usuarioCriacao;

    @LastModifiedBy
    @Column(name = "NM_USUARIO_ATUALIZACAO")
    private String usuarioAtualizacao;

    @CreatedDate
    @Column(name = "DH_CRIACAO")
    private LocalDateTime dataCriacao;

    @LastModifiedDate
    @Column(name = "DH_ATUALIZACAO")
    private LocalDateTime dataAtualizacao;
    
}

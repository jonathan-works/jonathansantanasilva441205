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
@Table(name = "USUARIO")
public class Usuario extends Auditoria{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NM_LOGIN")
    private String login;
    
    @Column(name = "DS_SENHA")
    private String senha;

}

package br.gov.mt.seplag.seletivo.ArtistHub.dto.auth;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String login;
    private String senha;
}

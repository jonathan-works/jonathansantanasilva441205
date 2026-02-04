package br.gov.mt.seplag.seletivo.ArtistHub.dto.album;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import lombok.Data;

@Data
public class AlbumResponseDTO {
    private Long id;
    private String titulo;
    private ArtistaResponseDTO artista;
}

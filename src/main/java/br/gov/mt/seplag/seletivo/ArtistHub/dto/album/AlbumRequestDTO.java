package br.gov.mt.seplag.seletivo.ArtistHub.dto.album;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import lombok.Data;

@Data
public class AlbumRequestDTO {
    private Long id;
    private String titulo;
    private ArtistaRequestDTO artista;
}

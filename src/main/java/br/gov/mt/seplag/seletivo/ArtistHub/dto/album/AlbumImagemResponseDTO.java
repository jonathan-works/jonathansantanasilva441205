package br.gov.mt.seplag.seletivo.ArtistHub.dto.album;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumImagemResponseDTO {
    private Long id;
    private String url;
    private String titulo;
}
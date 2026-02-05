package br.gov.mt.seplag.seletivo.ArtistHub.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Artista;

@Mapper(componentModel = "spring")
public interface ArtistaMapper {
        
    ArtistaResponseDTO toDTO(Artista model);

    Artista toEntity(ArtistaResponseDTO dto);

    Artista toEntity(ArtistaRequestDTO dto);

    List<ArtistaResponseDTO> toDTOList(List<Artista> model);

    List<Artista> toEntityList(List<ArtistaResponseDTO> dto);
}



    
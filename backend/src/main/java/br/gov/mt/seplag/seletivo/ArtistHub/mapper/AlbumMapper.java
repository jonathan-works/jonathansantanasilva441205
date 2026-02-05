package br.gov.mt.seplag.seletivo.ArtistHub.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Album;

@Mapper(componentModel = "spring")
public interface AlbumMapper {
    
    AlbumResponseDTO toDTO(Album model);

    Album toEntity(AlbumResponseDTO dto);

    Album toEntity(AlbumRequestDTO dto);

    List<AlbumResponseDTO> toDTOList(List<Album> model);

    List<Album> toEntityList(List<AlbumResponseDTO> dto);
}

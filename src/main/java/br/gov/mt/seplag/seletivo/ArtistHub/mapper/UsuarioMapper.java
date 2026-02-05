package br.gov.mt.seplag.seletivo.ArtistHub.mapper;

import org.mapstruct.Mapper;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.usuario.UsuarioRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.usuario.UsuarioResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioResponseDTO toDTO(Usuario model);
    Usuario toEntity(UsuarioResponseDTO model);
    Usuario toEntity(UsuarioRequestDTO model);
}

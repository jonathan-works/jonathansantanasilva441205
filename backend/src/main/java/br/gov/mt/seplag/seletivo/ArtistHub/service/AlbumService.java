package br.gov.mt.seplag.seletivo.ArtistHub.service;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.mapper.AlbumMapper;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Album;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AlbumService {
    private final AlbumRepository repository;
    private final AlbumMapper mapper;

    public Page<AlbumResponseDTO> findAll(Pageable pageable, Long artistaId) {
        if (artistaId != null) {
            return repository.findByArtistaId(artistaId, pageable).map(mapper::toDTO);
        }
       return repository.findAll(pageable).map(mapper::toDTO);
    }

    public AlbumResponseDTO save(AlbumRequestDTO dto) {
        var entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public AlbumResponseDTO getById(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Album não encontrado"));
        return mapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Album não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public AlbumResponseDTO update(Long id, AlbumRequestDTO dto) {

        Album album = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Album não encontrado"));

            album.setTitulo(dto.getTitulo());
        
        return mapper.toDTO(album);
    }
}

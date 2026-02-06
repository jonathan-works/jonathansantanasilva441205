package br.gov.mt.seplag.seletivo.ArtistHub.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.mapper.ArtistaMapper;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Artista;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.ArtistaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ArtistaService {
    private final ArtistaRepository repository;
    private final ArtistaMapper mapper;

    public List<ArtistaResponseDTO> findAll() {
       return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public ArtistaResponseDTO save(ArtistaRequestDTO dto) {
        var entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public ArtistaResponseDTO getById(Long id) {
        var entity = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Artista não encontrado"));
        return mapper.toDTO(entity);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Artista não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public ArtistaResponseDTO update(Long id, ArtistaRequestDTO dto) {
        Artista artista = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Artista não encontrado"));

        artista.setNome(dto.getNome());
        
        return mapper.toDTO(artista);
    }
}

package br.gov.mt.seplag.seletivo.ArtistHub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.mapper.ArtistaMapper;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Artista;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.ArtistaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Service
public class ArtistaService {
    private final ArtistaRepository repository;
    private final ArtistaMapper mapper;

    public List<ArtistaResponseDTO> findAll(String nome, Sort sort) {
        if (nome != null && !nome.isBlank()) {
            return repository.findByNomeContainingIgnoreCase(nome, sort).stream().map(mapper::toDTO).toList();
        }
       return repository.findAll(sort).stream().map(mapper::toDTO).toList();
    }

    public ArtistaResponseDTO save(ArtistaRequestDTO dto) {
        var entity = mapper.toEntity(dto);
        entity = repository.save(entity);
        return mapper.toDTO(entity);
    }

    @Transactional
    public ArtistaResponseDTO update(Long id, ArtistaRequestDTO dto) {

        Artista artista = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Artista não encontrado"));

            artista.setNome(dto.getNome());
        
        return mapper.toDTO(artista);
    }
}

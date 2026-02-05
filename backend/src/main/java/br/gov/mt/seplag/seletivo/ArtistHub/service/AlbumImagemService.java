package br.gov.mt.seplag.seletivo.ArtistHub.service;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumImagemResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.model.AlbumImage;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.AlbumImagemRepository;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.AlbumRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumImagemService {

    private final AlbumImagemRepository repository;
    private final AlbumRepository albumRepository;
    private final MinioService minioService;

    @Transactional
    public AlbumImagemResponseDTO upload(Long albumId, MultipartFile file) {
        var album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Álbum não encontrado"));

        String fileName = minioService.upload(file);

        AlbumImage imagem = new AlbumImage();
        imagem.setAlbum(album);
        imagem.setTitulo(fileName);
        
        imagem = repository.save(imagem);

        return AlbumImagemResponseDTO.builder()
                .id(imagem.getId())
                .titulo(imagem.getTitulo())
                .url(minioService.getPresignedUrl(imagem.getTitulo()))
                .build();
    }

    public List<AlbumImagemResponseDTO> getImagens(Long albumId) {
        return repository.findByAlbumId(albumId).stream()
                .map(img -> AlbumImagemResponseDTO.builder()
                        .id(img.getId())
                        .titulo(img.getTitulo())
                        .url(minioService.getPresignedUrl(img.getTitulo()))
                        .build())
                .toList();
    }
}
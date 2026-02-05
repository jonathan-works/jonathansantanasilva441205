package br.gov.mt.seplag.seletivo.ArtistHub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long>{
    Page<Album> findByArtistaId(Long artistaId, Pageable pageable);
}

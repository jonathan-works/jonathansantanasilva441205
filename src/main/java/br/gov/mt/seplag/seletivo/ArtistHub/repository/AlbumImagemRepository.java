package br.gov.mt.seplag.seletivo.ArtistHub.repository;

import br.gov.mt.seplag.seletivo.ArtistHub.model.AlbumImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlbumImagemRepository extends JpaRepository<AlbumImage, Long> {
    List<AlbumImage> findByAlbumId(Long albumId);
}
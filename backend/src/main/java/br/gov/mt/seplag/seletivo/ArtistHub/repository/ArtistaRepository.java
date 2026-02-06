
package br.gov.mt.seplag.seletivo.ArtistHub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long>{
    Page<Artista> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}

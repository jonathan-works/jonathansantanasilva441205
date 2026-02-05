
package br.gov.mt.seplag.seletivo.ArtistHub.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long>{
    List<Artista> findByNomeContainingIgnoreCase(String nome, Sort sort);
}

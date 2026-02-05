package br.gov.mt.seplag.seletivo.ArtistHub.repository;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionalRepository extends JpaRepository<Regional, Long> {
    List<Regional> findByAtivoTrue();
    Optional<Regional> findByExternalIdAndAtivoTrue(Long externalId);
}

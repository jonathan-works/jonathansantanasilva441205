package br.gov.mt.seplag.seletivo.ArtistHub.repository;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByLogin(String login);
}

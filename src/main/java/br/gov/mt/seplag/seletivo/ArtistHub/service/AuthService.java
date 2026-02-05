package br.gov.mt.seplag.seletivo.ArtistHub.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.gov.mt.seplag.seletivo.ArtistHub.config.security.JwtService;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.auth.AuthRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.auth.AuthResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.usuario.UsuarioRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.usuario.UsuarioResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.mapper.UsuarioMapper;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Usuario;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO token(AuthRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getSenha()
                    )
            );
        } catch (Exception e) {
            throw e;
        }

        var user = repository.findByLogin(request.getLogin())
                .orElseThrow(() -> {
                    return new RuntimeException("Usuário não encontrado");
                });
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponseDTO refreshToken() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Usuario userDetails) {
            var jwtToken = jwtService.generateToken(userDetails);
            return AuthResponseDTO.builder()
                    .token(jwtToken)
                    .build();
        }
        throw new RuntimeException("Não autorizado para renovação");
    }

    public UsuarioResponseDTO registrar(UsuarioRequestDTO request) {
        var user = Usuario.builder()
                .login(request.getLogin())
                .senha(passwordEncoder.encode(request.getSenha()))
                .build();

        return mapper.toDTO(repository.save(user));
    }
}

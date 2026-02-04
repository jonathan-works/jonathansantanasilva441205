package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.auth.AuthRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.auth.AuthResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação")
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService service;

    @Operation(summary = "Autenticar usuário", description = "Realiza o login e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    })
    @PostMapping("/token")
    public ResponseEntity<AuthResponseDTO> token(@RequestBody AuthRequestDTO request) {
        return ResponseEntity.ok(service.token(request));
    }   

    @Operation(summary = "Renovar token", description = "Gera um novo token JWT a partir de um token válido")
    @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Token renovado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Token inválido ou expirado")
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDTO> refreshToken() {
        return ResponseEntity.ok(service.refreshToken());
    }
}

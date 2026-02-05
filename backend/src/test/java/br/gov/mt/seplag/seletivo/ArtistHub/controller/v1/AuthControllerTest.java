package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.auth.AuthRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.auth.AuthResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.usuario.UsuarioRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.usuario.UsuarioResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService service;

    @InjectMocks
    private AuthController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldAuthenticate() throws Exception {
        AuthRequestDTO request = new AuthRequestDTO();
        request.setLogin("test@test.com");
        request.setSenha("password");

        AuthResponseDTO responseDTO = AuthResponseDTO.builder().token("token").build();
        when(service.token(any(AuthRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service).token(any(AuthRequestDTO.class));
    }

    @Test
    void shouldRefreshToken() throws Exception {
        AuthResponseDTO responseDTO = AuthResponseDTO.builder().token("newToken").build();
        when(service.refreshToken()).thenReturn(responseDTO);

        mockMvc.perform(post("/v1/auth/refresh-token"))
                .andExpect(status().isOk());

        verify(service).refreshToken();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setLogin("test@test.com");
        request.setSenha("password");

        when(service.registrar(any(UsuarioRequestDTO.class))).thenReturn(new UsuarioResponseDTO());

        mockMvc.perform(post("/v1/auth/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service).registrar(any(UsuarioRequestDTO.class));
    }
}

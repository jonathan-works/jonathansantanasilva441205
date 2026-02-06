package br.gov.mt.seplag.seletivo.ArtistHub;

import br.gov.mt.seplag.seletivo.ArtistHub.config.security.JwtService;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Album;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Artista;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Usuario;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.AlbumImagemRepository;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.AlbumRepository;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.ArtistaRepository;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
class GetByIdIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumImagemRepository albumImagemRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private MockMvc mockMvc;
    private String token;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        albumImagemRepository.deleteAll();
        albumRepository.deleteAll();
        artistaRepository.deleteAll();
        usuarioRepository.deleteAll();

        Usuario user = Usuario.builder()
                .login("testuser")
                .senha(passwordEncoder.encode("password"))
                .build();
        usuarioRepository.save(user);
        token = jwtService.generateToken(user);
    }

    @Test
    void shouldDeleteArtista() throws Exception {
        Artista artista = new Artista();
        artista.setNome("Artista Delete");
        artista = artistaRepository.save(artista);

        mockMvc.perform(delete("/v1/artista/" + artista.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/artista/" + artista.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAlbum() throws Exception {
        Artista artista = new Artista();
        artista.setNome("Artista Album Delete");
        artista = artistaRepository.save(artista);

        Album album = new Album();
        album.setTitulo("Album Delete");
        album.setArtista(artista);
        album = albumRepository.save(album);

        mockMvc.perform(delete("/v1/album/" + album.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/v1/album/" + album.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetArtistaById() throws Exception {
        Artista artista = new Artista();
        artista.setNome("Artista Teste");
        artista = artistaRepository.save(artista);

        mockMvc.perform(get("/v1/artista/" + artista.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Artista Teste"));
    }

    @Test
    void shouldGetAlbumById() throws Exception {
        Artista artista = new Artista();
        artista.setNome("Artista Album");
        artista = artistaRepository.save(artista);

        Album album = new Album();
        album.setTitulo("Album Teste");
        album.setArtista(artista);
        album = albumRepository.save(album);

        mockMvc.perform(get("/v1/album/" + album.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Album Teste"));
    }

    @Test
    void shouldReturn404WhenArtistaNotFound() throws Exception {
        mockMvc.perform(get("/v1/artista/999")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Or 500 depending on global exception handler
    }
}

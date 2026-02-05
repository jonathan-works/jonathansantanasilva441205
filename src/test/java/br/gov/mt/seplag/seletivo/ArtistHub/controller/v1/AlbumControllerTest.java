package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumImagemResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AlbumImagemService;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AlbumService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AlbumControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AlbumService albumService;

    @Mock
    private AlbumImagemService albumImagemService;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private AlbumController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.findAndRegisterModules();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(), new SortHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void shouldGetAll() throws Exception {
        when(albumService.findAll(any(Pageable.class), any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        mockMvc.perform(get("/v1/album"))
                .andExpect(status().isNoContent());

        verify(albumService).findAll(any(Pageable.class), any());
    }

    @Test
    void shouldGetAll_WithData() throws Exception {
        AlbumResponseDTO response = new AlbumResponseDTO();
        response.setId(1L);
        response.setTitulo("Album Title");
        
        List<AlbumResponseDTO> content = new ArrayList<>();
        content.add(response);

        when(albumService.findAll(any(), any())).thenReturn(new PageImpl<>(content, PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/v1/album"))
                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        verify(albumService).findAll(any(), any());
    }

    @Test
    void shouldSave() throws Exception {
        AlbumRequestDTO request = new AlbumRequestDTO();
        request.setTitulo("Novo Album");

        AlbumResponseDTO response = new AlbumResponseDTO();
        response.setId(1L);
        response.setTitulo("Novo Album");

        when(albumService.save(any(AlbumRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/v1/album")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(albumService).save(any(AlbumRequestDTO.class));
        verify(messagingTemplate).convertAndSend(eq("/topic/albuns"), any(AlbumResponseDTO.class));
    }

    @Test
    void shouldUpdate() throws Exception {
        AlbumRequestDTO request = new AlbumRequestDTO();
        request.setTitulo("sfasfasdfadsfa adfa");

        AlbumResponseDTO response = new AlbumResponseDTO();
        response.setId(1L);
        response.setTitulo("asdfadsfadsfa");

        when(albumService.update(eq(1L), any(AlbumRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/v1/album/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(albumService).update(eq(1L), any(AlbumRequestDTO.class));
    }

    @Test
    void shouldUploadImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "image.jpg", "image/jpeg", "content".getBytes());
        AlbumImagemResponseDTO response = AlbumImagemResponseDTO.builder().id(1L).url("url").build();

        when(albumImagemService.upload(eq(1L), any(MultipartFile.class))).thenReturn(response);

        mockMvc.perform(multipart("/v1/album/1/imagem").file(file))
                .andExpect(status().isOk());

        verify(albumImagemService).upload(eq(1L), any(MultipartFile.class));
    }

    @Test
    void shouldGetImages() throws Exception {
        AlbumImagemResponseDTO response = AlbumImagemResponseDTO.builder().id(1L).url("url").build();

        when(albumImagemService.getImagens(1L)).thenReturn(List.of(response));

        mockMvc.perform(get("/v1/album/1/imagem"))
                .andExpect(status().isOk());

        verify(albumImagemService).getImagens(1L);
    }
}

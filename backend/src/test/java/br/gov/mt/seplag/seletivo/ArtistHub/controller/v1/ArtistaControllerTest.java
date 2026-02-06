package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.ArtistaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArtistaControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ArtistaService service;

    @InjectMocks
    private ArtistaController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver(), new SortHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void shouldGetAll() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/artista"))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    void shouldGetAll_WithData() throws Exception {
        ArtistaResponseDTO response = new ArtistaResponseDTO();
        response.setId(1L);
        response.setNome("qwerq");
        
        when(service.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/v1/artista"))
                .andExpect(status().isOk());

        verify(service).findAll();
    }

    @Test
    void shouldSave() throws Exception {
        ArtistaRequestDTO request = new ArtistaRequestDTO();
        request.setNome("rqwerq");

        ArtistaResponseDTO response = new ArtistaResponseDTO();
        response.setId(1L);
        response.setNome("qerqwerq");

        when(service.save(any(ArtistaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/v1/artista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service).save(any(ArtistaRequestDTO.class));
    }

    @Test
    void shouldUpdate() throws Exception {
        ArtistaRequestDTO request = new ArtistaRequestDTO();
        request.setNome("adfadfadfasdfqerqe");

        ArtistaResponseDTO response = new ArtistaResponseDTO();
        response.setId(1L);
        response.setNome("adferqeradfadfasdf");

        when(service.update(eq(1L), any(ArtistaRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/v1/artista/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(service).update(eq(1L), any(ArtistaRequestDTO.class));
    }
}

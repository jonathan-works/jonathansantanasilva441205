package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Regional;
import br.gov.mt.seplag.seletivo.ArtistHub.service.RegionalIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegionalControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegionalIntegrationService service;

    @InjectMocks
    private RegionalController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldSynchronize() throws Exception {
        mockMvc.perform(post("/v1/integracao/regionais/sincronizar"))
                .andExpect(status().isOk());

        verify(service).synchronizeRegionais();
    }

    @Test
    void shouldFindAll() throws Exception {
        when(service.findAll()).thenReturn(List.of(new Regional()));

        mockMvc.perform(get("/v1/integracao/regionais")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(service).findAll();
    }
}

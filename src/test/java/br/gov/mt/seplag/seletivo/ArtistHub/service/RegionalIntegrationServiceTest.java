package br.gov.mt.seplag.seletivo.ArtistHub.service;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.integration.RegionalExternalDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Regional;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.RegionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionalIntegrationServiceTest {

    @Mock
    private RegionalRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RegionalIntegrationService service;

    private static final String EXTERNAL_API_URL = "https://integrador-argus-api.geia.vip/v1/regionais";

    @Test
    void shouldSynchronize_CreateNewRegional() {
        // Mock API Response
        RegionalExternalDTO dto = new RegionalExternalDTO();
        dto.setId(1L);
        dto.setNome("Regional 1");
        RegionalExternalDTO[] apiResponse = {dto};
        when(restTemplate.getForObject(EXTERNAL_API_URL, RegionalExternalDTO[].class)).thenReturn(apiResponse);

        when(repository.findByAtivoTrue()).thenReturn(new ArrayList<>());

        service.synchronizeRegionais();

        verify(repository, times(1)).save(any(Regional.class));
    }

    @Test
    void shouldSynchronize_UpdateRegional_WhenNameChanged() {
        RegionalExternalDTO dto = new RegionalExternalDTO();
        dto.setId(1L);
        dto.setNome("Regional 1 Updated");
        RegionalExternalDTO[] apiResponse = {dto};
        when(restTemplate.getForObject(EXTERNAL_API_URL, RegionalExternalDTO[].class)).thenReturn(apiResponse);

        Regional local = Regional.builder().id(10L).externalId(1L).nome("Regional 1").ativo(true).build();
        when(repository.findByAtivoTrue()).thenReturn(List.of(local));

        service.synchronizeRegionais();

        verify(repository, times(2)).save(any(Regional.class));
    }

    @Test
    void shouldSynchronize_InactivateRegional_WhenRemovedFromApi() {
        RegionalExternalDTO[] apiResponse = {};
        when(restTemplate.getForObject(EXTERNAL_API_URL, RegionalExternalDTO[].class)).thenReturn(apiResponse);

        Regional local = Regional.builder().id(10L).externalId(1L).nome("Regional 1").ativo(true).build();
        when(repository.findByAtivoTrue()).thenReturn(List.of(local));

        service.synchronizeRegionais();

        verify(repository, times(1)).save(argThat(r -> !r.getAtivo()));
    }
}

package br.gov.mt.seplag.seletivo.ArtistHub.service;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.integration.RegionalExternalDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.model.Regional;
import br.gov.mt.seplag.seletivo.ArtistHub.repository.RegionalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionalIntegrationService {

    private final RegionalRepository repository;
    private final RestTemplate restTemplate;
    private static final String EXTERNAL_API_URL = "https://integrador-argus-api.geia.vip/v1/regionais";

    @Transactional
    public void synchronizeRegionais() {
        RegionalExternalDTO[] response = restTemplate.getForObject(EXTERNAL_API_URL, RegionalExternalDTO[].class);
        if (response == null) {
            log.warn("API externa retornou nulo.");
            return;
        }
        List<RegionalExternalDTO> externalList = Arrays.asList(response);
        Map<Long, RegionalExternalDTO> externalMap = externalList.stream()
                .collect(Collectors.toMap(RegionalExternalDTO::getId, Function.identity()));

        List<Regional> localList = repository.findByAtivoTrue();
        Map<Long, Regional> localMap = localList.stream()
                .collect(Collectors.toMap(Regional::getExternalId, Function.identity()));

        int created = 0;
        int updated = 0;
        int inactivated = 0;

        for (RegionalExternalDTO external : externalList) {
            if (localMap.containsKey(external.getId())) {
                Regional local = localMap.get(external.getId());
                if (!local.getNome().equals(external.getNome())) {
                    local.setAtivo(false);
                    repository.save(local);
                    
                    createRegional(external);
                    updated++;
                }
            } else {
                createRegional(external);
                created++;
            }
        }

        for (Regional local : localList) {
            if (!externalMap.containsKey(local.getExternalId())) {
                local.setAtivo(false);
                repository.save(local);
                inactivated++;
            }
        }

        log.info("Sincronizacao concluida. Criados: {}, Atualizados (recriados): {}, Inativados: {}", created, updated, inactivated);
    }

    private void createRegional(RegionalExternalDTO dto) {
        Regional newRegional = Regional.builder()
                .externalId(dto.getId())
                .nome(dto.getNome())
                .ativo(true)
                .build();
        repository.save(newRegional);
    }

    public List<Regional> findAll() {
        return repository.findByAtivoTrue();
    }
}

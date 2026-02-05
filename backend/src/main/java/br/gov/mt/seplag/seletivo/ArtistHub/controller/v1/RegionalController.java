package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import br.gov.mt.seplag.seletivo.ArtistHub.model.Regional;
import br.gov.mt.seplag.seletivo.ArtistHub.service.RegionalIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/integracao/regionais")
@RequiredArgsConstructor
@Tag(name = "Integração Regional", description = "Endpoints para integração de regionais")
public class RegionalController {

    private final RegionalIntegrationService service;

    @Operation(summary = "Sincronizar regionais", description = "Busca dados da API externa e atualiza base local")
    @PostMapping("/sincronizar")
    public ResponseEntity<Void> synchronize() {
        service.synchronizeRegionais();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar regionais", description = "Lista todas as regionais ativas")
    @GetMapping
    public ResponseEntity<List<Regional>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}

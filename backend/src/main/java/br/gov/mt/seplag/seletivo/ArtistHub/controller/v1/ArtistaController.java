package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/artista")
@Tag(name = "Artista", description = "Endpoints de artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    @Operation(summary = "Listar artistas")
    @GetMapping
    public ResponseEntity<List<ArtistaResponseDTO>> getAll() {
        var response = artistaService.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar artista")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Criar artista")
    @PostMapping
    public ResponseEntity<ArtistaResponseDTO> save(@RequestBody ArtistaRequestDTO dto) {
        var response = artistaService.save(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar artista por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> getById(@PathVariable Long id) {
        var response = artistaService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar artista")
    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> update(@PathVariable("id") Long id, @RequestBody ArtistaRequestDTO dto) {
        var response = artistaService.update(id, dto);
        return ResponseEntity.ok(response);
    }
    
}

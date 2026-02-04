package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.artista.ArtistaResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.ArtistaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/artista")
@Tag(name = "Artista", description = "Endpoints de artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    @GetMapping
    public ResponseEntity<List<ArtistaResponseDTO>> getAll() {
        var response = artistaService.findAll();
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ArtistaResponseDTO> save(@RequestBody ArtistaRequestDTO dto) {
        var response = artistaService.save(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistaResponseDTO> update(@PathVariable("id") Long id, @RequestBody ArtistaRequestDTO dto) {
        var response = artistaService.update(id, dto);
        return ResponseEntity.ok(response);
    }
    
}

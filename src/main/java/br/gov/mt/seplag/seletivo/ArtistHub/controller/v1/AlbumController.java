package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AlbumService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@AllArgsConstructor
@RequestMapping("/v1/album")
@Tag(name = "Álbum", description = "Endpoints de álbuns")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<Page<AlbumResponseDTO>> getAll(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var response = albumService.findAll(pageable);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> save(@RequestBody AlbumRequestDTO dto) {
        var response = albumService.save(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> update(@PathVariable("id") Long id, @RequestBody AlbumRequestDTO dto) {
        var response = albumService.update(id, dto);
        return ResponseEntity.ok(response);
    }
}

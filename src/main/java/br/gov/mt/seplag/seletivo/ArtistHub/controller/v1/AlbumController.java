package br.gov.mt.seplag.seletivo.ArtistHub.controller.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumImagemResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumRequestDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.dto.album.AlbumResponseDTO;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AlbumImagemService;
import br.gov.mt.seplag.seletivo.ArtistHub.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/album")
@Tag(name = "Álbum", description = "Endpoints de álbuns")
public class AlbumController {
    private final AlbumService albumService;
    private final AlbumImagemService service;
    private final SimpMessagingTemplate messagingTemplate;

    @Operation(summary = "Listar álbuns", parameters = {
        @Parameter(name = "page", description = "Número da página (0..N)", schema = @Schema(type = "integer", defaultValue = "0")),
        @Parameter(name = "size", description = "Itens por página", schema = @Schema(type = "integer", defaultValue = "10")),
        @Parameter(name = "sort", description = "Critério de ordenação (ex: id,asc)", schema = @Schema(type = "string"))
    })
    @GetMapping
    public ResponseEntity<Page<AlbumResponseDTO>> getAll(@Parameter(hidden = true) @PageableDefault(size = 10, sort = "id") Pageable pageable, 
                                                         @RequestParam(required = false) Long artistaId) {
        var response = albumService.findAll(pageable, artistaId);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> save(@RequestBody AlbumRequestDTO dto) {
        var response = albumService.save(dto);
        messagingTemplate.convertAndSend("/topic/albuns", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> update(@PathVariable("id") Long id, @RequestBody AlbumRequestDTO dto) {
        var response = albumService.update(id, dto);
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Upload de imagem", description = "Faz upload da capa do álbum")
    @PostMapping(value = "/{id}/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AlbumImagemResponseDTO> upload(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(service.upload(id, file));
    }

    @Operation(summary = "Listar imagens", description = "Retorna URLs pré-assinadas das imagens do álbum")
    @GetMapping("/{id}/imagem")
    public ResponseEntity<List<AlbumImagemResponseDTO>> getImagens(@PathVariable Long id) {
        var response = service.getImagens(id);
        return response.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(response);
    }
}

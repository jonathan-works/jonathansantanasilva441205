import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AlbumFacade } from '../album.facade';
import { Observable } from 'rxjs';
import { AlbumImagemResponseDTO, AlbumResponseDTO } from '../../../core/models/album.model';

@Component({
  selector: 'app-album-image',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './album-image.component.html'
})
export class AlbumImageComponent implements OnInit {
  albumId: number | null = null;
  album$: Observable<AlbumResponseDTO> | null = null;
  images$: Observable<AlbumImagemResponseDTO[]> | null = null;

  constructor(
    private route: ActivatedRoute,
    private albumFacade: AlbumFacade
  ) {}

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.albumId = Number(idParam);
      this.album$ = this.albumFacade.getAlbum(this.albumId);
      this.loadImages();
    }
  }

  loadImages() {
    if (this.albumId) {
      this.images$ = this.albumFacade.getImages(this.albumId);
    }
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];
    if (file && this.albumId) {
      this.albumFacade.uploadImage(this.albumId, file).subscribe(() => {
        this.loadImages();
      });
    }
  }
}

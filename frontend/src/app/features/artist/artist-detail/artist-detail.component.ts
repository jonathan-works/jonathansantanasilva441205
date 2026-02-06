import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ArtistFacade } from '../artist.facade';
import { AlbumFacade } from '../../album/album.facade';
import { Observable } from 'rxjs';
import { Artist } from '../../../core/models/artist.model';
import { AlbumResponseDTO } from '../../../core/models/album.model';

@Component({
  selector: 'app-artist-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './artist-detail.component.html'
})
export class ArtistDetailComponent implements OnInit {
  artist$: Observable<Artist | null>;
  loading$: Observable<boolean>;
  
  albums$: Observable<AlbumResponseDTO[]>;
  loadingAlbums$: Observable<boolean>;

  constructor(
    private route: ActivatedRoute,
    private artistFacade: ArtistFacade,
    private albumFacade: AlbumFacade
  ) {
    this.artist$ = this.artistFacade.selectedArtist$;
    this.loading$ = this.artistFacade.loading$;
    this.albums$ = this.albumFacade.albums$;
    this.loadingAlbums$ = this.albumFacade.loading$;
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.artistFacade.loadArtist(id);
      // Fetch all albums for the artist (size=1000) or handle pagination in detail view
      this.albumFacade.loadAlbums('titulo,asc', 0, 1000, Number(id));
    }
  }
}

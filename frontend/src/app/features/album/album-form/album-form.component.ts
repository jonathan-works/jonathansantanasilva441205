import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { AlbumFacade } from '../album.facade';
import { ArtistFacade } from '../../artist/artist.facade';
import { Observable, take } from 'rxjs';
import { Artist } from '../../../core/models/artist.model';
import { AlbumRequestDTO } from '../../../core/models/album.model';

@Component({
  selector: 'app-album-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './album-form.component.html'
})
export class AlbumFormComponent implements OnInit {
  form: FormGroup;
  isEdit = false;
  albumId: number | null = null;
  artists$: Observable<Artist[]>;

  constructor(
    private fb: FormBuilder,
    private albumFacade: AlbumFacade,
    private artistFacade: ArtistFacade,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.form = this.fb.group({
      titulo: ['', Validators.required],
      artistaId: [null, Validators.required]
    });
    this.artists$ = this.artistFacade.artists$;
  }

  ngOnInit() {
    this.loadArtists();
    
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam && idParam !== 'new') {
      this.albumId = Number(idParam);
      this.isEdit = true;
      this.loadAlbum(this.albumId);
    }
  }

  loadArtists() {
    this.artistFacade.loadArtists();
  }

  loadAlbum(id: number) {
    this.albumFacade.getAlbum(id).subscribe(album => {
      if (album) {
        this.form.patchValue({
          titulo: album.titulo,
          artistaId: album.artista?.id
        });
      }
    });
  }

  onSubmit() {
    if (this.form.invalid) return;

    const formValue = this.form.value;
    
    this.artists$.pipe(take(1)).subscribe(artists => {
      const selectedArtist = artists.find(a => a.id == formValue.artistaId);
      
      if (!selectedArtist) {
        return;
      }

      const albumData: AlbumRequestDTO = {
        titulo: formValue.titulo,
        artista: {
          id: selectedArtist.id,
          nome: selectedArtist.nome
        }
      };

      if (this.isEdit && this.albumId) {
        albumData.id = this.albumId;
        this.albumFacade.updateAlbum(this.albumId, albumData).subscribe(() => {
          this.router.navigate(['/albums']);
        });
      } else {
        this.albumFacade.createAlbum(albumData).subscribe(() => {
          this.router.navigate(['/albums']);
        });
      }
    });
  }
}

import { Injectable } from '@angular/core';
import { BehaviorSubject, finalize, tap } from 'rxjs';
import { ArtistService } from '../../core/services/artist.service';
import { NotificationService } from '../../core/services/notification.service';
import { Artist, ArtistRequest, PaginatedResponse } from '../../core/models/artist.model';

@Injectable({
  providedIn: 'root'
})
export class ArtistFacade {
  private artistsSubject = new BehaviorSubject<Artist[]>([]);
  public artists$ = this.artistsSubject.asObservable();

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  private selectedArtistSubject = new BehaviorSubject<Artist | null>(null);
  public selectedArtist$ = this.selectedArtistSubject.asObservable();

  constructor(
    private artistService: ArtistService,
    private notificationService: NotificationService
  ) {}

  loadArtists(sort: string = 'asc', search: string = '') {
    this.loadingSubject.next(true);
    this.artistService.getArtists(sort, search)
      .pipe(finalize(() => this.loadingSubject.next(false)))
      .subscribe({
        next: (response: any) => {
          console.log('Artists loaded:', response);
          if (response && response.content) {
            this.artistsSubject.next(response.content);
          } else if (Array.isArray(response)) {
            // Fallback if API returns a list directly
            this.artistsSubject.next(response);
          } else {
            console.warn('Unexpected response format:', response);
            this.artistsSubject.next([]);
          }
        },
        error: (error) => {
          console.error('Error loading artists:', error);
          this.artistsSubject.next([]);
          this.notificationService.showError('Erro ao carregar artistas');
        }
      });
  }

  loadArtist(id: string) {
    this.loadingSubject.next(true);
    this.artistService.getArtist(Number(id))
      .pipe(finalize(() => this.loadingSubject.next(false)))
      .subscribe({
        next: (artist) => {
          this.selectedArtistSubject.next(artist);
        },
        error: (err) => {
          console.error(err);
          this.notificationService.showError('Erro ao carregar detalhes do artista');
        }
      });
  }

  createArtist(artist: ArtistRequest) {
    return this.artistService.createArtist(artist).pipe(
      tap(() => {
        this.loadArtists();
        this.notificationService.showSuccess('Artista cadastrado com sucesso!');
      })
    );
  }

  updateArtist(id: string, artist: ArtistRequest) {
    return this.artistService.updateArtist(Number(id), artist).pipe(
      tap(() => {
        this.loadArtists();
        this.notificationService.showSuccess('Artista atualizado com sucesso!');
      })
    );
  }
}

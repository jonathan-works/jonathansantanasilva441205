import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, finalize, tap } from 'rxjs';
import { AlbumService } from '../../core/services/album.service';
import { NotificationService } from '../../core/services/notification.service';
import { AlbumRequestDTO, AlbumResponseDTO, AlbumImagemResponseDTO } from '../../core/models/album.model';

@Injectable({
  providedIn: 'root'
})
export class AlbumFacade {
  private albumsSubject = new BehaviorSubject<AlbumResponseDTO[]>([]);
  public albums$ = this.albumsSubject.asObservable();

  private totalElementsSubject = new BehaviorSubject<number>(0);
  public totalElements$ = this.totalElementsSubject.asObservable();

  private totalPagesSubject = new BehaviorSubject<number>(0);
  public totalPages$ = this.totalPagesSubject.asObservable();

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor(
    private albumService: AlbumService,
    private notificationService: NotificationService
  ) {}

  loadAlbums(sort: string = 'titulo,asc', page: number = 0, size: number = 10, artistaId?: number) {
    this.loadingSubject.next(true);
    this.albumService.getAll(page, size, sort, artistaId)
      .pipe(finalize(() => this.loadingSubject.next(false)))
      .subscribe({
        next: (response) => {
          if (response && response.content) {
            this.albumsSubject.next(response.content);
            this.totalElementsSubject.next(response.totalElements || 0);
            this.totalPagesSubject.next(response.totalPages || 0);
          } else {
            console.warn('Unexpected response format:', response);
            this.albumsSubject.next([]);
            this.totalElementsSubject.next(0);
            this.totalPagesSubject.next(0);
          }
        },
        error: (error) => {
          console.error('Error loading albums:', error);
          this.albumsSubject.next([]);
          this.totalElementsSubject.next(0);
          this.totalPagesSubject.next(0);
          this.notificationService.showError('Erro ao carregar álbuns');
        }
      });
  }

  getAlbum(id: number): Observable<AlbumResponseDTO> {
    return this.albumService.getById(id);
  }

  createAlbum(album: AlbumRequestDTO): Observable<AlbumResponseDTO> {
    return this.albumService.create(album).pipe(
      tap(() => {
        this.loadAlbums();
        this.notificationService.showSuccess('Álbum criado com sucesso!');
      })
    );
  }

  updateAlbum(id: number, album: AlbumRequestDTO): Observable<AlbumResponseDTO> {
    return this.albumService.update(id, album).pipe(
      tap(() => {
        this.loadAlbums();
        this.notificationService.showSuccess('Álbum atualizado com sucesso!');
      })
    );
  }

  uploadImage(id: number, file: File): Observable<AlbumImagemResponseDTO> {
    return this.albumService.uploadImagem(id, file).pipe(
      tap(() => {
        this.notificationService.showSuccess('Imagem enviada com sucesso!');
      })
    );
  }

  getImages(id: number): Observable<AlbumImagemResponseDTO[]> {
    return this.albumService.getImagens(id);
  }
}

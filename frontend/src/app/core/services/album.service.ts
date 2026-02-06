import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AlbumRequestDTO, AlbumResponseDTO, AlbumImagemResponseDTO, PageAlbumResponseDTO } from '../models/album.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlbumService {
  
  private apiUrl = `${environment.apiUrl}/v1/album`;

  constructor(private http: HttpClient) {}

  getAll(page: number = 0, size: number = 10, sort: string = 'titulo,asc', artistaId?: number): Observable<PageAlbumResponseDTO> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sort', sort);

    if (artistaId) {
      params = params.set('artistaId', artistaId.toString());
    }

    return this.http.get<PageAlbumResponseDTO>(this.apiUrl, { params });
  }

  getById(id: number): Observable<AlbumResponseDTO> {
    return this.http.get<AlbumResponseDTO>(`${this.apiUrl}/${id}`);
  }

  create(album: AlbumRequestDTO): Observable<AlbumResponseDTO> {
    return this.http.post<AlbumResponseDTO>(this.apiUrl, album);
  }

  update(id: number, album: AlbumRequestDTO): Observable<AlbumResponseDTO> {
    return this.http.put<AlbumResponseDTO>(`${this.apiUrl}/${id}`, album);
  }

  uploadImagem(id: number, file: File): Observable<AlbumImagemResponseDTO> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<AlbumImagemResponseDTO>(`${this.apiUrl}/${id}/imagem`, formData);
  }

  getImagens(id: number): Observable<AlbumImagemResponseDTO[]> {
    return this.http.get<AlbumImagemResponseDTO[]>(`${this.apiUrl}/${id}/imagem`);
  }
}

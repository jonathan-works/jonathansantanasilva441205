import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Artist, ArtistRequest, AlbumRequest, PaginatedResponse } from '../models/artist.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ArtistService {
  
  private apiUrl = `${environment.apiUrl}/v1/artista`;
  private uploadUrl = `${environment.apiUrl}/v1/upload`;

  constructor(private http: HttpClient) {}

  getArtists(sort: string = 'asc', nome: string = ''): Observable<PaginatedResponse<Artist>> {

    let params = new HttpParams()
      .set('page', '0')
      .set('size', '1000') // Fetch all (effectively)
      .set('sort', sort);

    if (nome) {
      params = params.set('nome', nome);
    }

    return this.http.get<PaginatedResponse<Artist>>(this.apiUrl, { params });
  }

  getArtist(id: number): Observable<Artist> {
    return this.http.get<Artist>(`${this.apiUrl}/${id}`);
  }

  createArtist(artist: ArtistRequest): Observable<Artist> {
    return this.http.post<Artist>(this.apiUrl, artist);
  }

  updateArtist(id: number, artist: ArtistRequest): Observable<Artist> {
    return this.http.put<Artist>(`${this.apiUrl}/${id}`, artist);
  }

  addAlbum(artistId: number, albumData: AlbumRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/${artistId}/albums`, albumData);
  }

  uploadFile(file: File): Observable<{ url: string }> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<{ url: string }>(this.uploadUrl, formData);
  }
}

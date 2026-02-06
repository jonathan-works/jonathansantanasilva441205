import { ArtistRequest, Artist } from "./artist.model";

export interface AlbumRequestDTO {
  id?: number;
  titulo: string;
  artista: ArtistRequest;
}

export interface AlbumResponseDTO {
  id: number;
  titulo: string;
  artista: Artist;
}

export interface AlbumImagemResponseDTO {
  id: number;
  url: string;
  titulo: string;
}

export interface PageAlbumResponseDTO {
  content: AlbumResponseDTO[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}

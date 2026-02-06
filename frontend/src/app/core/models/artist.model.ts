export interface Artist {
  id: number;
  nome: string;
}

export interface ArtistRequest {
  id?: number;
  nome: string;
}

export interface Album {
  id: number;
  titulo: string;
  artista?: Artist;
}

export interface AlbumRequest {
  id?: number;
  titulo: string;
  artista?: ArtistRequest;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
  empty: boolean;
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AlbumFacade } from '../album.facade';
import { Observable } from 'rxjs';
import { AlbumResponseDTO } from '../../../core/models/album.model';

@Component({
  selector: 'app-album-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './album-list.component.html',
  styleUrl: './album-list.component.scss'
})
export class AlbumListComponent implements OnInit {
  albums$: Observable<AlbumResponseDTO[]>;
  loading$: Observable<boolean>;
  totalElements$: Observable<number>;
  totalPages$: Observable<number>;
  
  sort = 'titulo,asc';
  page = 0;
  size = 10;

  constructor(private albumFacade: AlbumFacade) {
    this.albums$ = this.albumFacade.albums$;
    this.loading$ = this.albumFacade.loading$;
    this.totalElements$ = this.albumFacade.totalElements$;
    this.totalPages$ = this.albumFacade.totalPages$;
  }

  ngOnInit() {
    this.loadAlbums();
  }

  loadAlbums() {
    this.albumFacade.loadAlbums(this.sort, this.page, this.size);
  }

  onSortChange(newSort: string) {
    this.sort = newSort;
    this.page = 0; // Reset to first page on sort change
    this.loadAlbums();
  }

  onPageChange(newPage: number) {
    this.page = newPage;
    this.loadAlbums();
  }
}

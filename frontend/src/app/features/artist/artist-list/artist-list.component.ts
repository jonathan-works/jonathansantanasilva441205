import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { ArtistFacade } from '../artist.facade';
import { debounceTime, distinctUntilChanged, startWith, map } from 'rxjs/operators';
import { Observable, combineLatest } from 'rxjs';
import { Artist } from '../../../core/models/artist.model';

@Component({
  selector: 'app-artist-list',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './artist-list.component.html'
})
export class ArtistListComponent implements OnInit {
  searchControl = new FormControl('');
  artists$: Observable<Artist[]>;
  loading$: Observable<boolean>;
  
  sort = 'asc';

  constructor(private artistFacade: ArtistFacade) {
    this.loading$ = this.artistFacade.loading$;
    
    // Client-side filtering
    this.artists$ = combineLatest([
      this.artistFacade.artists$,
      this.searchControl.valueChanges.pipe(
        startWith(''),
        debounceTime(300),
        distinctUntilChanged()
      )
    ]).pipe(
      map(([artists, search]) => {
        const term = (search || '').toLowerCase();
        return artists.filter(artist => artist.nome.toLowerCase().includes(term));
      })
    );
  }

  ngOnInit() {
    this.loadArtists();
  }

  loadArtists() {
    // Load all artists (empty search string) to filter on client side
    this.artistFacade.loadArtists(this.sort, '');
  }

  onSortChange(newSort: string) {
    this.sort = newSort;
    this.loadArtists();
  }
}

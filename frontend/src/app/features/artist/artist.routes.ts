import { Routes } from '@angular/router';
import { ArtistListComponent } from './artist-list/artist-list.component';
import { ArtistDetailComponent } from './artist-detail/artist-detail.component';
import { ArtistFormComponent } from './artist-form/artist-form.component';

export const ARTIST_ROUTES: Routes = [
  { path: '', component: ArtistListComponent, data: { breadcrumb: 'Artistas' } },
  { path: 'new', component: ArtistFormComponent, data: { breadcrumb: 'Novo Artista' } }, // Order matters: 'new' before ':id'
  { path: ':id', component: ArtistDetailComponent, data: { breadcrumb: 'Detalhes' } },
  { path: ':id/edit', component: ArtistFormComponent, data: { breadcrumb: 'Editar' } }
];

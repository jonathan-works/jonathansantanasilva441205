import { Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { HomeComponent } from './features/home/home.component';
import { AlbumListComponent } from './features/album/album-list/album-list.component';
import { AlbumFormComponent } from './features/album/album-form/album-form.component';
import { AlbumImageComponent } from './features/album/album-image/album-image.component';
import { RegionalListComponent } from './features/regional/regional-list/regional-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'albums', component: AlbumListComponent, canActivate: [AuthGuard] },
  { path: 'albums/new', component: AlbumFormComponent, canActivate: [AuthGuard] },
  { path: 'albums/:id/edit', component: AlbumFormComponent, canActivate: [AuthGuard] },
  { path: 'albums/:id/images', component: AlbumImageComponent, canActivate: [AuthGuard] },
  { path: 'regional', component: RegionalListComponent, canActivate: [AuthGuard] },
  { 
    path: 'auth', 
    loadChildren: () => import('./features/auth/auth.routes').then(m => m.AUTH_ROUTES) 
  },
  { 
    path: 'artists', 
    canActivate: [AuthGuard],
    loadChildren: () => import('./features/artist/artist.routes').then(m => m.ARTIST_ROUTES) 
  }
];

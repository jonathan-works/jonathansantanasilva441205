import { Component } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router, NavigationEnd } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NotificationComponent } from './shared/components/notification/notification.component';
import { BreadcrumbComponent } from './shared/components/breadcrumb/breadcrumb.component';
import { AuthService } from './core/services/auth.service';
import { WebSocketService } from './core/services/websocket.service';
import { Observable, combineLatest } from 'rxjs';
import { map, filter, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive, NotificationComponent, BreadcrumbComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ArtistHub';
  showMenu$: Observable<boolean>;
  isMobileMenuOpen = false;

  constructor(
    private authService: AuthService, 
    private router: Router,
    private webSocketService: WebSocketService // Inject to initialize
  ) {
    const isLoggedIn$ = authService.user$.pipe(map(u => !!u && !!u.token));
    
    const isNotAuthRoute$ = this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map((event: NavigationEnd) => event.urlAfterRedirects),
      startWith(this.router.url),
      map(url => !url.includes('/auth'))
    );

    this.showMenu$ = combineLatest([isLoggedIn$, isNotAuthRoute$]).pipe(
      map(([isLoggedIn, isNotAuth]) => isLoggedIn && isNotAuth)
    );
  }

  logout() {
    this.isMobileMenuOpen = false;
    this.authService.logout();
  }

  toggleMobileMenu() {
    this.isMobileMenuOpen = !this.isMobileMenuOpen;
  }
}

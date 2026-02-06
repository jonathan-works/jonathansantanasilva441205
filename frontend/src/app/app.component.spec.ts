import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { AuthService } from './core/services/auth.service';
import { WebSocketService } from './core/services/websocket.service';
import { Router } from '@angular/router';
import { of, BehaviorSubject } from 'rxjs';
import { RouterTestingModule } from '@angular/router/testing';
import { NotificationComponent } from './shared/components/notification/notification.component';
import { BreadcrumbComponent } from './shared/components/breadcrumb/breadcrumb.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let authServiceMock: any;
  let webSocketServiceMock: any;

  beforeEach(async () => {
    authServiceMock = {
      user$: new BehaviorSubject(null),
      logout: jasmine.createSpy('logout')
    };

    webSocketServiceMock = {
      // Add any methods if called in constructor or ngOnInit
    };

    await TestBed.configureTestingModule({
      imports: [
        AppComponent, 
        RouterTestingModule, 
        HttpClientTestingModule,
        NotificationComponent,
        BreadcrumbComponent
      ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: WebSocketService, useValue: webSocketServiceMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it(`should have the 'ArtistHub' title`, () => {
    expect(component.title).toEqual('ArtistHub');
  });

  it('should call authService.logout when logout is called', () => {
    component.logout();
    expect(authServiceMock.logout).toHaveBeenCalled();
  });

  it('should toggle mobile menu', () => {
    expect(component.isMobileMenuOpen).toBeFalse();
    component.toggleMobileMenu();
    expect(component.isMobileMenuOpen).toBeTrue();
    component.toggleMobileMenu();
    expect(component.isMobileMenuOpen).toBeFalse();
  });
});

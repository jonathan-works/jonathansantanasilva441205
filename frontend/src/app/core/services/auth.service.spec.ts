import { TestBed } from '@angular/core/testing';
import { AuthService } from './auth.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let routerMock: any;

  beforeEach(() => {
    routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AuthService,
        { provide: Router, useValue: routerMock }
      ]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and set user', () => {
    const mockResponse = { token: 'fake-token' };
    const credentials = { username: 'test', password: 'password' };

    service.login(credentials).subscribe(response => {
      expect(response.token).toBe('fake-token');
      expect(service.token).toBe('fake-token');
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/v1/auth/token`);
    expect(req.request.method).toBe('POST');
    req.flush(mockResponse);
  });

  it('should logout and clear user', () => {
    // Setup initial state
    service.setUser({ username: 'test', token: 'token' });
    expect(service.token).toBe('token');

    service.logout();

    expect(service.token).toBeNull();
    expect(localStorage.getItem('user')).toBeNull();
    expect(routerMock.navigate).toHaveBeenCalledWith(['/auth/login']);
  });
});

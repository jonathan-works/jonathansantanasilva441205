import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let authServiceMock: any;
  let routerMock: any;

  beforeEach(async () => {
    authServiceMock = {
      login: jasmine.createSpy('login').and.returnValue(of({ token: 'fake-token' }))
    };

    routerMock = {
      navigate: jasmine.createSpy('navigate')
    };

    await TestBed.configureTestingModule({
      imports: [LoginComponent, ReactiveFormsModule],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize form with empty values', () => {
    expect(component.form.get('login')?.value).toBe('');
    expect(component.form.get('senha')?.value).toBe('');
  });

  it('should be invalid when empty', () => {
    expect(component.form.valid).toBeFalse();
  });

  it('should be valid when filled', () => {
    component.form.controls['login'].setValue('testuser');
    component.form.controls['senha'].setValue('password');
    expect(component.form.valid).toBeTrue();
  });

  it('should call authService.login on submit when valid', () => {
    component.form.controls['login'].setValue('testuser');
    component.form.controls['senha'].setValue('password');
    
    component.onSubmit();

    expect(authServiceMock.login).toHaveBeenCalledWith({ login: 'testuser', senha: 'password' });
    expect(routerMock.navigate).toHaveBeenCalledWith(['/artists']);
  });

  it('should not call authService.login when invalid', () => {
    component.onSubmit();
    expect(authServiceMock.login).not.toHaveBeenCalled();
  });

  it('should set error message on login failure', () => {
    authServiceMock.login.and.returnValue(throwError(() => new Error('Login failed')));
    component.form.controls['login'].setValue('testuser');
    component.form.controls['senha'].setValue('password');

    component.onSubmit();

    expect(component.error).toBe('Login falhou. Verifique suas credenciais.');
  });
});

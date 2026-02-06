import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { NotificationService } from '../services/notification.service';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const notificationService = inject(NotificationService);
  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ocorreu um erro inesperado.';

      if (error.error instanceof ErrorEvent) {
        errorMessage = `Erro: ${error.error.message}`;
      } else {
        if (error.status === 401) {
          errorMessage = 'Sessão expirada ou não autorizada. Por favor, faça login novamente.';
        } else if (error.status === 403) {
          errorMessage = 'Você não tem permissão para acessar este recurso.';
          router.navigate(['/auth/login']);
        } else if (error.status === 404) {
          errorMessage = 'Recurso não encontrado.';
        } else if (error.status === 429) {
           errorMessage = error.error?.message || error.error || 'Muitas requisições. Tente novamente mais tarde. (RateLimit 10)';
        } else if (error.error && error.error.message) {
          errorMessage = error.error.message;
        } else {
           errorMessage = `Erro no servidor (Código: ${error.status})`;
        }
      }

      notificationService.showError(errorMessage);
      return throwError(() => error);
    })
  );
};

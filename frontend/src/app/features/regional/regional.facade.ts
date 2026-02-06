import { Injectable } from '@angular/core';
import { BehaviorSubject, finalize, tap } from 'rxjs';
import { RegionalService } from '../../core/services/regional.service';
import { NotificationService } from '../../core/services/notification.service';
import { Regional } from '../../core/models/regional.model';

@Injectable({
  providedIn: 'root'
})
export class RegionalFacade {
  private regionalsSubject = new BehaviorSubject<Regional[]>([]);
  public regionals$ = this.regionalsSubject.asObservable();

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();

  constructor(
    private regionalService: RegionalService,
    private notificationService: NotificationService
  ) {}

  loadRegionals() {
    this.loadingSubject.next(true);
    this.regionalService.getAll()
      .pipe(finalize(() => this.loadingSubject.next(false)))
      .subscribe({
        next: (regionals) => {
          this.regionalsSubject.next(regionals);
        },
        error: (error) => {
          console.error('Error loading regionals:', error);
          this.regionalsSubject.next([]);
          this.notificationService.showError('Erro ao carregar regionais');
        }
      });
  }

  synchronizeRegionals() {
    this.loadingSubject.next(true);
    this.regionalService.synchronize()
      .pipe(finalize(() => this.loadingSubject.next(false)))
      .subscribe({
        next: () => {
          this.notificationService.showSuccess('Sincronização iniciada com sucesso!');
          this.loadRegionals();
        },
        error: (error) => {
          console.error('Error synchronizing regionals:', error);
          this.notificationService.showError('Erro ao sincronizar regionais');
        }
      });
  }
}

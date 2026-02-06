import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface Notification {
  id: string;
  type: 'success' | 'error' | 'info';
  message: string;
  duration?: number;
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private notificationsSubject = new BehaviorSubject<Notification[]>([]);
  public notifications$ = this.notificationsSubject.asObservable();

  showError(message: string, duration: number = 5000) {
    this.addNotification({ id: this.generateId(), type: 'error', message, duration });
  }

  showSuccess(message: string, duration: number = 3000) {
    this.addNotification({ id: this.generateId(), type: 'success', message, duration });
  }

  showInfo(message: string, duration: number = 3000) {
    this.addNotification({ id: this.generateId(), type: 'info', message, duration });
  }

  remove(id: string) {
    const current = this.notificationsSubject.value;
    this.notificationsSubject.next(current.filter(n => n.id !== id));
  }

  private addNotification(notification: Notification) {
    const current = this.notificationsSubject.value;
    this.notificationsSubject.next([...current, notification]);

    if (notification.duration && notification.duration > 0) {
      setTimeout(() => {
        this.remove(notification.id);
      }, notification.duration);
    }
  }

  private generateId(): string {
    return Math.random().toString(36).substring(2, 9);
  }
}

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService, Notification } from '../../../core/services/notification.service';
import { animate, style, transition, trigger } from '@angular/animations';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-notification',
  standalone: true,
  imports: [CommonModule],
  animations: [
    trigger('flyInOut', [
      transition(':enter', [
        style({ transform: 'translateY(100%)', opacity: 0 }),
        animate('300ms ease-out', style({ transform: 'translateY(0)', opacity: 1 }))
      ]),
      transition(':leave', [
        animate('200ms ease-in', style({ transform: 'translateY(100%)', opacity: 0 }))
      ])
    ])
  ],
  template: `
    <div class="fixed bottom-4 right-4 z-50 flex flex-col gap-2">
      <div *ngFor="let notification of notifications$ | async" 
           [@flyInOut]
           class="px-6 py-4 rounded shadow-lg text-white flex items-center justify-between min-w-[300px]"
           [ngClass]="{
             'bg-red-500': notification.type === 'error',
             'bg-green-500': notification.type === 'success',
             'bg-blue-500': notification.type === 'info'
           }">
        <span>{{ notification.message }}</span>
        <button (click)="close(notification.id)" class="ml-4 text-white hover:text-gray-200 focus:outline-none">
          ✕
        </button>
      </div>
    </div>
  `
})
export class NotificationComponent {
  notifications$: Observable<Notification[]>;

  constructor(private notificationService: NotificationService) {
    this.notifications$ = this.notificationService.notifications$;
  }

  close(id: string) {
    this.notificationService.remove(id);
  }
}

import { Injectable, OnDestroy } from '@angular/core';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { AuthService } from './auth.service';
import { NotificationService } from './notification.service';
import { Subscription } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WebSocketService implements OnDestroy {
  private client: Client;
  private userSubscription: Subscription;

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService
  ) {
    this.client = new Client({
      webSocketFactory: () => new SockJS(`${environment.apiUrl}/ws`),
      debug: (str) => {
        // console.log('STOMP: ' + str); 
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.client.onConnect = (frame) => {
      console.log('WebSocket Connected');
      this.subscribeToNotifications();
      this.subscribeToAlbuns();
    };

    this.client.onStompError = (frame) => {
      console.error('Broker reported error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
    };

    // Monitor auth state
    this.userSubscription = this.authService.user$.subscribe(user => {
      if (user && user.token) {
        this.connect(user.token);
      } else {
        this.disconnect();
      }
    });
  }

  private connect(token: string) {
    if (this.client.active) {
      return;
    }
    this.client.connectHeaders = {
      Authorization: `Bearer ${token}`
    };
    this.client.activate();
  }

  private disconnect() {
    if (this.client.active) {
      this.client.deactivate();
    }
  }

  private subscribeToNotifications() {
    this.client.subscribe('/topic/notifications', (message: Message) => {
      this.handleMessage(message);
    });
  }

  private subscribeToAlbuns() {
    this.client.subscribe('/topic/albuns', (message: Message) => {
      this.handleMessage(message);
    });
  }

  private handleMessage(message: Message) {
    if (message.body) {
      let content = message.body;
      try {
        const json = JSON.parse(message.body);
        content = json.message || json.content || content;
      } catch (e) {
        // not json
      }
      this.notificationService.showInfo(content);
    }
  }

  ngOnDestroy() {
    this.userSubscription.unsubscribe();
    this.disconnect();
  }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Regional } from '../models/regional.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegionalService {
  
  private apiUrl = `${environment.apiUrl}/v1/integracao/regionais`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Regional[]> {
    return this.http.get<Regional[]>(this.apiUrl);
  }

  synchronize(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/sincronizar`, {});
  }
}

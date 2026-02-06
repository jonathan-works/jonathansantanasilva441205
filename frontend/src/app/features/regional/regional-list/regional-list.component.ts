import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RegionalFacade } from '../regional.facade';
import { Observable } from 'rxjs';
import { Regional } from '../../../core/models/regional.model';

@Component({
  selector: 'app-regional-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './regional-list.component.html',
  styleUrl: './regional-list.component.scss'
})
export class RegionalListComponent implements OnInit {
  regionals$: Observable<Regional[]>;
  loading$: Observable<boolean>;

  constructor(private regionalFacade: RegionalFacade) {
    this.regionals$ = this.regionalFacade.regionals$;
    this.loading$ = this.regionalFacade.loading$;
  }

  ngOnInit() {
    this.regionalFacade.loadRegionals();
  }

  onSynchronize() {
    this.regionalFacade.synchronizeRegionals();
  }
}

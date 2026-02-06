import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, NavigationEnd, ActivatedRoute } from '@angular/router';
import { filter } from 'rxjs/operators';

export interface Breadcrumb {
  label: string;
  url: string;
}

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <nav *ngIf="breadcrumbs.length > 0" class="bg-gray-100 px-4 py-2 rounded-md w-full mb-4" aria-label="Breadcrumb">
      <ol class="list-reset flex text-gray-700">
        <li>
          <a routerLink="/" class="text-blue-600 hover:underline">Início</a>
        </li>
        <li *ngFor="let breadcrumb of breadcrumbs">
          <span class="mx-2 text-gray-500">/</span>
          <a *ngIf="breadcrumb.url; else lastBreadcrumb" [routerLink]="breadcrumb.url" class="text-blue-600 hover:underline">
            {{ breadcrumb.label }}
          </a>
          <ng-template #lastBreadcrumb>
            <span class="text-gray-500">{{ breadcrumb.label }}</span>
          </ng-template>
        </li>
      </ol>
    </nav>
  `
})
export class BreadcrumbComponent implements OnInit {
  breadcrumbs: Breadcrumb[] = [];

  constructor(private router: Router, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.breadcrumbs = this.createBreadcrumbs(this.activatedRoute.root);
    });
  }

  private createBreadcrumbs(route: ActivatedRoute, url: string = '', breadcrumbs: Breadcrumb[] = []): Breadcrumb[] {
    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {
      const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
      if (routeURL !== '') {
        url += `/${routeURL}`;
      }

      const label = child.snapshot.data['breadcrumb'];
      if (label) {
        breadcrumbs.push({ label, url });
      }

      return this.createBreadcrumbs(child, url, breadcrumbs);
    }
    
    return breadcrumbs;
  }
}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Chevre } from 'app/shared/model/chevre.model';
import { ChevreService } from './chevre.service';
import { ChevreComponent } from './chevre.component';
import { ChevreDetailComponent } from './chevre-detail.component';
import { ChevreUpdateComponent } from './chevre-update.component';
import { ChevreDeletePopupComponent } from './chevre-delete-dialog.component';
import { IChevre } from 'app/shared/model/chevre.model';

@Injectable({ providedIn: 'root' })
export class ChevreResolve implements Resolve<IChevre> {
  constructor(private service: ChevreService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChevre> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Chevre>) => response.ok),
        map((chevre: HttpResponse<Chevre>) => chevre.body)
      );
    }
    return of(new Chevre());
  }
}

export const chevreRoute: Routes = [
  {
    path: '',
    component: ChevreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Chevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChevreDetailComponent,
    resolve: {
      chevre: ChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Chevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChevreUpdateComponent,
    resolve: {
      chevre: ChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Chevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChevreUpdateComponent,
    resolve: {
      chevre: ChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Chevres'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const chevrePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ChevreDeletePopupComponent,
    resolve: {
      chevre: ChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Chevres'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

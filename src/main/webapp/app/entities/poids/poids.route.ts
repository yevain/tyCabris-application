import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Poids } from 'app/shared/model/poids.model';
import { PoidsService } from './poids.service';
import { PoidsComponent } from './poids.component';
import { PoidsDetailComponent } from './poids-detail.component';
import { PoidsUpdateComponent } from './poids-update.component';
import { PoidsDeletePopupComponent } from './poids-delete-dialog.component';
import { IPoids } from 'app/shared/model/poids.model';

@Injectable({ providedIn: 'root' })
export class PoidsResolve implements Resolve<IPoids> {
  constructor(private service: PoidsService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPoids> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Poids>) => response.ok),
        map((poids: HttpResponse<Poids>) => poids.body)
      );
    }
    return of(new Poids());
  }
}

export const poidsRoute: Routes = [
  {
    path: '',
    component: PoidsComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poids'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PoidsDetailComponent,
    resolve: {
      poids: PoidsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poids'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PoidsUpdateComponent,
    resolve: {
      poids: PoidsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poids'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PoidsUpdateComponent,
    resolve: {
      poids: PoidsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poids'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const poidsPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PoidsDeletePopupComponent,
    resolve: {
      poids: PoidsResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Poids'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

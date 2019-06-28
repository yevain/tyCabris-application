import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ParcChevre } from 'app/shared/model/parc-chevre.model';
import { ParcChevreService } from './parc-chevre.service';
import { ParcChevreComponent } from './parc-chevre.component';
import { ParcChevreDetailComponent } from './parc-chevre-detail.component';
import { ParcChevreUpdateComponent } from './parc-chevre-update.component';
import { ParcChevreDeletePopupComponent } from './parc-chevre-delete-dialog.component';
import { IParcChevre } from 'app/shared/model/parc-chevre.model';

@Injectable({ providedIn: 'root' })
export class ParcChevreResolve implements Resolve<IParcChevre> {
  constructor(private service: ParcChevreService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParcChevre> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ParcChevre>) => response.ok),
        map((parcChevre: HttpResponse<ParcChevre>) => parcChevre.body)
      );
    }
    return of(new ParcChevre());
  }
}

export const parcChevreRoute: Routes = [
  {
    path: '',
    component: ParcChevreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ParcChevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParcChevreDetailComponent,
    resolve: {
      parcChevre: ParcChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ParcChevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParcChevreUpdateComponent,
    resolve: {
      parcChevre: ParcChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ParcChevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParcChevreUpdateComponent,
    resolve: {
      parcChevre: ParcChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ParcChevres'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const parcChevrePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParcChevreDeletePopupComponent,
    resolve: {
      parcChevre: ParcChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ParcChevres'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

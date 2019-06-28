import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { EvenementChevre } from 'app/shared/model/evenement-chevre.model';
import { EvenementChevreService } from './evenement-chevre.service';
import { EvenementChevreComponent } from './evenement-chevre.component';
import { EvenementChevreDetailComponent } from './evenement-chevre-detail.component';
import { EvenementChevreUpdateComponent } from './evenement-chevre-update.component';
import { EvenementChevreDeletePopupComponent } from './evenement-chevre-delete-dialog.component';
import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';

@Injectable({ providedIn: 'root' })
export class EvenementChevreResolve implements Resolve<IEvenementChevre> {
  constructor(private service: EvenementChevreService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEvenementChevre> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<EvenementChevre>) => response.ok),
        map((evenementChevre: HttpResponse<EvenementChevre>) => evenementChevre.body)
      );
    }
    return of(new EvenementChevre());
  }
}

export const evenementChevreRoute: Routes = [
  {
    path: '',
    component: EvenementChevreComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvenementChevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EvenementChevreDetailComponent,
    resolve: {
      evenementChevre: EvenementChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvenementChevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EvenementChevreUpdateComponent,
    resolve: {
      evenementChevre: EvenementChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvenementChevres'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EvenementChevreUpdateComponent,
    resolve: {
      evenementChevre: EvenementChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvenementChevres'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const evenementChevrePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EvenementChevreDeletePopupComponent,
    resolve: {
      evenementChevre: EvenementChevreResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'EvenementChevres'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

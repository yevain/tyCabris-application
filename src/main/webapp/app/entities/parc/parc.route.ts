import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Parc } from 'app/shared/model/parc.model';
import { ParcService } from './parc.service';
import { ParcComponent } from './parc.component';
import { ParcDetailComponent } from './parc-detail.component';
import { ParcUpdateComponent } from './parc-update.component';
import { ParcDeletePopupComponent } from './parc-delete-dialog.component';
import { IParc } from 'app/shared/model/parc.model';

@Injectable({ providedIn: 'root' })
export class ParcResolve implements Resolve<IParc> {
  constructor(private service: ParcService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParc> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Parc>) => response.ok),
        map((parc: HttpResponse<Parc>) => parc.body)
      );
    }
    return of(new Parc());
  }
}

export const parcRoute: Routes = [
  {
    path: '',
    component: ParcComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Parcs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ParcDetailComponent,
    resolve: {
      parc: ParcResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Parcs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ParcUpdateComponent,
    resolve: {
      parc: ParcResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Parcs'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ParcUpdateComponent,
    resolve: {
      parc: ParcResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Parcs'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const parcPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ParcDeletePopupComponent,
    resolve: {
      parc: ParcResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Parcs'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

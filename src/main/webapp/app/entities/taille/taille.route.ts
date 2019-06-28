import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Taille } from 'app/shared/model/taille.model';
import { TailleService } from './taille.service';
import { TailleComponent } from './taille.component';
import { TailleDetailComponent } from './taille-detail.component';
import { TailleUpdateComponent } from './taille-update.component';
import { TailleDeletePopupComponent } from './taille-delete-dialog.component';
import { ITaille } from 'app/shared/model/taille.model';

@Injectable({ providedIn: 'root' })
export class TailleResolve implements Resolve<ITaille> {
  constructor(private service: TailleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITaille> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Taille>) => response.ok),
        map((taille: HttpResponse<Taille>) => taille.body)
      );
    }
    return of(new Taille());
  }
}

export const tailleRoute: Routes = [
  {
    path: '',
    component: TailleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tailles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TailleDetailComponent,
    resolve: {
      taille: TailleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tailles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TailleUpdateComponent,
    resolve: {
      taille: TailleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tailles'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TailleUpdateComponent,
    resolve: {
      taille: TailleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tailles'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const taillePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TailleDeletePopupComponent,
    resolve: {
      taille: TailleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Tailles'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

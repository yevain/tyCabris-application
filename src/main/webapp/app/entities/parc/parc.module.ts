import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TyCabrisApplicationSharedModule } from 'app/shared';
import {
  ParcComponent,
  ParcDetailComponent,
  ParcUpdateComponent,
  ParcDeletePopupComponent,
  ParcDeleteDialogComponent,
  parcRoute,
  parcPopupRoute
} from './';

const ENTITY_STATES = [...parcRoute, ...parcPopupRoute];

@NgModule({
  imports: [TyCabrisApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ParcComponent, ParcDetailComponent, ParcUpdateComponent, ParcDeleteDialogComponent, ParcDeletePopupComponent],
  entryComponents: [ParcComponent, ParcUpdateComponent, ParcDeleteDialogComponent, ParcDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationParcModule {}

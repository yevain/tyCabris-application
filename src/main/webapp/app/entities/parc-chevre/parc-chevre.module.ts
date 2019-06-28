import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TyCabrisApplicationSharedModule } from 'app/shared';
import {
  ParcChevreComponent,
  ParcChevreDetailComponent,
  ParcChevreUpdateComponent,
  ParcChevreDeletePopupComponent,
  ParcChevreDeleteDialogComponent,
  parcChevreRoute,
  parcChevrePopupRoute
} from './';

const ENTITY_STATES = [...parcChevreRoute, ...parcChevrePopupRoute];

@NgModule({
  imports: [TyCabrisApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ParcChevreComponent,
    ParcChevreDetailComponent,
    ParcChevreUpdateComponent,
    ParcChevreDeleteDialogComponent,
    ParcChevreDeletePopupComponent
  ],
  entryComponents: [ParcChevreComponent, ParcChevreUpdateComponent, ParcChevreDeleteDialogComponent, ParcChevreDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationParcChevreModule {}

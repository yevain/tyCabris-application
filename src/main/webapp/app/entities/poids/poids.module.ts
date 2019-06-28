import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TyCabrisApplicationSharedModule } from 'app/shared';
import {
  PoidsComponent,
  PoidsDetailComponent,
  PoidsUpdateComponent,
  PoidsDeletePopupComponent,
  PoidsDeleteDialogComponent,
  poidsRoute,
  poidsPopupRoute
} from './';

const ENTITY_STATES = [...poidsRoute, ...poidsPopupRoute];

@NgModule({
  imports: [TyCabrisApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PoidsComponent, PoidsDetailComponent, PoidsUpdateComponent, PoidsDeleteDialogComponent, PoidsDeletePopupComponent],
  entryComponents: [PoidsComponent, PoidsUpdateComponent, PoidsDeleteDialogComponent, PoidsDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationPoidsModule {}

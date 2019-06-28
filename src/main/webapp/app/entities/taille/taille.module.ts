import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TyCabrisApplicationSharedModule } from 'app/shared';
import {
  TailleComponent,
  TailleDetailComponent,
  TailleUpdateComponent,
  TailleDeletePopupComponent,
  TailleDeleteDialogComponent,
  tailleRoute,
  taillePopupRoute
} from './';

const ENTITY_STATES = [...tailleRoute, ...taillePopupRoute];

@NgModule({
  imports: [TyCabrisApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TailleComponent, TailleDetailComponent, TailleUpdateComponent, TailleDeleteDialogComponent, TailleDeletePopupComponent],
  entryComponents: [TailleComponent, TailleUpdateComponent, TailleDeleteDialogComponent, TailleDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationTailleModule {}

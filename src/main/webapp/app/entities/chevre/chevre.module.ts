import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TyCabrisApplicationSharedModule } from 'app/shared';
import {
  ChevreComponent,
  ChevreDetailComponent,
  ChevreUpdateComponent,
  ChevreDeletePopupComponent,
  ChevreDeleteDialogComponent,
  chevreRoute,
  chevrePopupRoute
} from './';

const ENTITY_STATES = [...chevreRoute, ...chevrePopupRoute];

@NgModule({
  imports: [TyCabrisApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ChevreComponent, ChevreDetailComponent, ChevreUpdateComponent, ChevreDeleteDialogComponent, ChevreDeletePopupComponent],
  entryComponents: [ChevreComponent, ChevreUpdateComponent, ChevreDeleteDialogComponent, ChevreDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationChevreModule {}

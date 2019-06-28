import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TyCabrisApplicationSharedModule } from 'app/shared';
import {
  EvenementChevreComponent,
  EvenementChevreDetailComponent,
  EvenementChevreUpdateComponent,
  EvenementChevreDeletePopupComponent,
  EvenementChevreDeleteDialogComponent,
  evenementChevreRoute,
  evenementChevrePopupRoute
} from './';

const ENTITY_STATES = [...evenementChevreRoute, ...evenementChevrePopupRoute];

@NgModule({
  imports: [TyCabrisApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EvenementChevreComponent,
    EvenementChevreDetailComponent,
    EvenementChevreUpdateComponent,
    EvenementChevreDeleteDialogComponent,
    EvenementChevreDeletePopupComponent
  ],
  entryComponents: [
    EvenementChevreComponent,
    EvenementChevreUpdateComponent,
    EvenementChevreDeleteDialogComponent,
    EvenementChevreDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationEvenementChevreModule {}

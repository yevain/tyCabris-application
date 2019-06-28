import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'chevre',
        loadChildren: './chevre/chevre.module#TyCabrisApplicationChevreModule'
      },
      {
        path: 'parc',
        loadChildren: './parc/parc.module#TyCabrisApplicationParcModule'
      },
      {
        path: 'parc-chevre',
        loadChildren: './parc-chevre/parc-chevre.module#TyCabrisApplicationParcChevreModule'
      },
      {
        path: 'evenement',
        loadChildren: './evenement/evenement.module#TyCabrisApplicationEvenementModule'
      },
      {
        path: 'poids',
        loadChildren: './poids/poids.module#TyCabrisApplicationPoidsModule'
      },
      {
        path: 'evenement-chevre',
        loadChildren: './evenement-chevre/evenement-chevre.module#TyCabrisApplicationEvenementChevreModule'
      },
      {
        path: 'taille',
        loadChildren: './taille/taille.module#TyCabrisApplicationTailleModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationEntityModule {}

import { NgModule } from '@angular/core';

import { TyCabrisApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [TyCabrisApplicationSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [TyCabrisApplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class TyCabrisApplicationSharedCommonModule {}

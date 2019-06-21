import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TyCabrisApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [TyCabrisApplicationSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [TyCabrisApplicationSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TyCabrisApplicationSharedModule {
  static forRoot() {
    return {
      ngModule: TyCabrisApplicationSharedModule
    };
  }
}

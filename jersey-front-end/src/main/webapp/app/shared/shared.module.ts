import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { JersySharedLibsModule, JersySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [JersySharedLibsModule, JersySharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JersySharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JersySharedModule {
  static forRoot() {
    return {
      ngModule: JersySharedModule
    };
  }
}

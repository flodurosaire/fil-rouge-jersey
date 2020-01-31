import { NgModule } from '@angular/core';

import { JersySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [JersySharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [JersySharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class JersySharedCommonModule {}

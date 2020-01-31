import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JersySharedModule } from 'app/shared';
import { HOME_ROUTE, DashboardComponent } from './';

@NgModule({
  imports: [JersySharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [DashboardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JersyDashboardModule {}

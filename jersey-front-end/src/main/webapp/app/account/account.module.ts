import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JersySharedModule } from 'app/shared';

import { PasswordStrengthBarComponent,  PasswordComponent, SettingsComponent, accountState } from './';

@NgModule({
  imports: [JersySharedModule, RouterModule.forChild(accountState)],
  declarations: [ PasswordComponent, PasswordStrengthBarComponent, SettingsComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JersyAccountModule {}

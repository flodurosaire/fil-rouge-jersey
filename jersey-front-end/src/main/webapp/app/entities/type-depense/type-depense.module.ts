import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JersySharedModule } from 'app/shared';
import {
  TypeDepenseComponent,
  TypeDepenseDetailComponent,
  TypeDepenseUpdateComponent,
  TypeDepenseDeletePopupComponent,
  TypeDepenseDeleteDialogComponent,
  typeDepenseRoute,
  typeDepensePopupRoute
} from './';

const ENTITY_STATES = [...typeDepenseRoute, ...typeDepensePopupRoute];

@NgModule({
  imports: [JersySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TypeDepenseComponent,
    TypeDepenseDetailComponent,
    TypeDepenseUpdateComponent,
    TypeDepenseDeleteDialogComponent,
    TypeDepenseDeletePopupComponent
  ],
  entryComponents: [TypeDepenseComponent, TypeDepenseUpdateComponent, TypeDepenseDeleteDialogComponent, TypeDepenseDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JersyTypeDepenseModule {}

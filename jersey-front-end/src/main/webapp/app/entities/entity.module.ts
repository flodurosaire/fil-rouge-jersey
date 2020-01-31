import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'client',
        loadChildren: './client/client.module#JersyClientModule'
      },
      {
        path: 'facture',
        loadChildren: './facture/facture.module#JersyFactureModule'
      },
      {
        path: 'produit',
        loadChildren: './produit/produit.module#JersyProduitModule'
      },
      {
        path: 'depense',
        loadChildren: './depense/depense.module#JersyDepenseModule'
      },
      {
        path: 'type-depense',
        loadChildren: './type-depense/type-depense.module#JersyTypeDepenseModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JersyEntityModule {}

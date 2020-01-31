import { IDetailsFacture } from 'app/shared/model/details-facture.model';

export interface IProduit {
  id?: number;
  libelle?: string;
  prix?: number;
  qteStock?: number;
  description?: string;
  produits?: IDetailsFacture[];
}

export class Produit implements IProduit {
  constructor(
    public id?: number,
    public libelle?: string,
    public prix?: number,
    public qteStock?: number,
    public description?: string,
    public produits?: IDetailsFacture[]
  ) {}
}

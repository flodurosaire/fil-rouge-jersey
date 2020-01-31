export interface IDetailsFacture {
  id?: number;
  qteProduit?: number;
  factureId?: number;
  produitId?: number;
  produitName?: string;
  produitPrix?: number;
  description?: string;
}

export class DetailsFacture implements IDetailsFacture {
  constructor(
    public id?: number,
    public qteProduit?: number,
    public factureId?: number,
    public produitId?: number,
    public produitName?: string,
    public description?: string,
    public produitPrix?: number
  ) {}
}

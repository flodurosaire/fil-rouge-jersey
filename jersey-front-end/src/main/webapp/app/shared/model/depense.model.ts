import { Moment } from 'moment';

export interface IDepense {
  id?: number;
  libelle?: string;
  montant?: number;
  description?: string;
  dateFacturation?: Moment;
  typeDepenseId?: number;
  typeDepenseName?: string;
}

export class Depense implements IDepense {
  constructor(
    public id?: number,
    public libelle?: string,
    public montant?: number,
    public description?: string,
    public dateFacturation?: Moment,
    public typeDepenseId?: number,
    public typeDepenseName?: string
  ) {}
}

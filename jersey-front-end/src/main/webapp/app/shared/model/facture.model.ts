import { Moment } from 'moment';
import { IDetailsFacture } from 'app/shared/model/details-facture.model';
import { Client } from './client.model';

export interface IFacture {
  id?: number;
  dateFacturation?: Moment;
  clientId?: number;
  factures?: IDetailsFacture[];
  clientName?: string;
}

export class Facture implements IFacture {
  constructor(
    public id?: number,
    public dateFacturation?: Moment,
    public clientId?: number,
    public clientName?: string,
    public factures?: IDetailsFacture[]
  ) {}
}

import { IFacture } from 'app/shared/model/facture.model';
import { User } from 'app/core';

export interface IClient {
  id?: number;
  adresse?: string;
  localite?: string;
  categorie?: string;
  compte?: string;
  userId?: number;
  clientName?: string;
  // user: User;
  clients?: IFacture[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public adresse?: string,
    public localite?: string,
    public categorie?: string,
    public userId?: number,
    // public user: User,
    public clients?: IFacture[],
    public compte?: string,
    public clientName?: string
  ) {}
}

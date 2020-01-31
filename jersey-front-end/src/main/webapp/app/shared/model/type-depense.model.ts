import { Moment } from 'moment';
import { IDepense } from 'app/shared/model/depense.model';

export interface ITypeDepense {
  id?: number;
  libelle?: string;
  description?: string;
  dateFacturation?: Moment;
  depenses?: IDepense[];
}

export class TypeDepense implements ITypeDepense {
  constructor(
    public id?: number,
    public libelle?: string,
    public description?: string,
    public dateFacturation?: Moment,
    public depenses?: IDepense[]
  ) {}
}

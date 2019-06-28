import { Moment } from 'moment';

export interface IParcChevre {
  id?: number;
  entree?: Moment;
  sortie?: Moment;
  parcNom?: string;
  parcId?: number;
  chevreNom?: string;
  chevreId?: number;
}

export class ParcChevre implements IParcChevre {
  constructor(
    public id?: number,
    public entree?: Moment,
    public sortie?: Moment,
    public parcNom?: string,
    public parcId?: number,
    public chevreNom?: string,
    public chevreId?: number
  ) {}
}

import { Moment } from 'moment';

export interface IEvenementChevre {
  id?: number;
  date?: Moment;
  evenementNom?: string;
  evenementId?: number;
  chevreNom?: string;
  chevreId?: number;
}

export class EvenementChevre implements IEvenementChevre {
  constructor(
    public id?: number,
    public date?: Moment,
    public evenementNom?: string,
    public evenementId?: number,
    public chevreNom?: string,
    public chevreId?: number
  ) {}
}

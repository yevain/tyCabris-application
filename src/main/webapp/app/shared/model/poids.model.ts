import { Moment } from 'moment';

export interface IPoids {
  id?: number;
  valeur?: number;
  date?: Moment;
  chevreNom?: string;
  chevreId?: number;
}

export class Poids implements IPoids {
  constructor(public id?: number, public valeur?: number, public date?: Moment, public chevreNom?: string, public chevreId?: number) {}
}

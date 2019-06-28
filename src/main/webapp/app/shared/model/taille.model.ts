import { Moment } from 'moment';

export interface ITaille {
  id?: number;
  valeur?: number;
  date?: Moment;
  chevreNom?: string;
  chevreId?: number;
}

export class Taille implements ITaille {
  constructor(public id?: number, public valeur?: number, public date?: Moment, public chevreNom?: string, public chevreId?: number) {}
}

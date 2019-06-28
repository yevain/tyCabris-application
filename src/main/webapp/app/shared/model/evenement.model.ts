import { IEvenement } from 'app/shared/model/evenement.model';
import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';

export interface IEvenement {
  id?: number;
  nom?: string;
  occurence?: number;
  suivants?: IEvenement[];
  evenementChevres?: IEvenementChevre[];
  evenementNom?: string;
  evenementId?: number;
}

export class Evenement implements IEvenement {
  constructor(
    public id?: number,
    public nom?: string,
    public occurence?: number,
    public suivants?: IEvenement[],
    public evenementChevres?: IEvenementChevre[],
    public evenementNom?: string,
    public evenementId?: number
  ) {}
}

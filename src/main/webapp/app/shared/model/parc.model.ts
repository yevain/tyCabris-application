import { IParcChevre } from 'app/shared/model/parc-chevre.model';

export interface IParc {
  id?: number;
  nom?: string;
  parcChevres?: IParcChevre[];
}

export class Parc implements IParc {
  constructor(public id?: number, public nom?: string, public parcChevres?: IParcChevre[]) {}
}

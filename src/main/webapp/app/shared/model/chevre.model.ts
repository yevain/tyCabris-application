import { Moment } from 'moment';
import { IPoids } from 'app/shared/model/poids.model';
import { ITaille } from 'app/shared/model/taille.model';
import { IParcChevre } from 'app/shared/model/parc-chevre.model';
import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';

export interface IChevre {
  id?: number;
  nom?: string;
  matricule?: string;
  surnom?: string;
  naissance?: Moment;
  present?: boolean;
  pereId?: number;
  mereId?: number;
  poids?: IPoids[];
  tailles?: ITaille[];
  parcChevres?: IParcChevre[];
  evenementChevres?: IEvenementChevre[];
}

export class Chevre implements IChevre {
  constructor(
    public id?: number,
    public nom?: string,
    public matricule?: string,
    public surnom?: string,
    public naissance?: Moment,
    public present?: boolean,
    public pereId?: number,
    public mereId?: number,
    public poids?: IPoids[],
    public tailles?: ITaille[],
    public parcChevres?: IParcChevre[],
    public evenementChevres?: IEvenementChevre[],
  ) {
    this.present = this.present || false;
  }
}

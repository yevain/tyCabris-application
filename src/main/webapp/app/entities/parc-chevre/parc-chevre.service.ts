import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParcChevre } from 'app/shared/model/parc-chevre.model';

type EntityResponseType = HttpResponse<IParcChevre>;
type EntityArrayResponseType = HttpResponse<IParcChevre[]>;

@Injectable({ providedIn: 'root' })
export class ParcChevreService {
  public resourceUrl = SERVER_API_URL + 'api/parc-chevres';

  constructor(protected http: HttpClient) {}

  create(parcChevre: IParcChevre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parcChevre);
    return this.http
      .post<IParcChevre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(parcChevre: IParcChevre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parcChevre);
    return this.http
      .put<IParcChevre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IParcChevre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IParcChevre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(parcChevre: IParcChevre): IParcChevre {
    const copy: IParcChevre = Object.assign({}, parcChevre, {
      entree: parcChevre.entree != null && parcChevre.entree.isValid() ? parcChevre.entree.format(DATE_FORMAT) : null,
      sortie: parcChevre.sortie != null && parcChevre.sortie.isValid() ? parcChevre.sortie.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.entree = res.body.entree != null ? moment(res.body.entree) : null;
      res.body.sortie = res.body.sortie != null ? moment(res.body.sortie) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((parcChevre: IParcChevre) => {
        parcChevre.entree = parcChevre.entree != null ? moment(parcChevre.entree) : null;
        parcChevre.sortie = parcChevre.sortie != null ? moment(parcChevre.sortie) : null;
      });
    }
    return res;
  }
}

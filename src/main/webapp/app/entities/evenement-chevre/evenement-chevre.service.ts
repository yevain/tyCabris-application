import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvenementChevre } from 'app/shared/model/evenement-chevre.model';

type EntityResponseType = HttpResponse<IEvenementChevre>;
type EntityArrayResponseType = HttpResponse<IEvenementChevre[]>;

@Injectable({ providedIn: 'root' })
export class EvenementChevreService {
  public resourceUrl = SERVER_API_URL + 'api/evenement-chevres';

  constructor(protected http: HttpClient) {}

  create(evenementChevre: IEvenementChevre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(evenementChevre);
    return this.http
      .post<IEvenementChevre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(evenementChevre: IEvenementChevre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(evenementChevre);
    return this.http
      .put<IEvenementChevre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEvenementChevre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEvenementChevre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(evenementChevre: IEvenementChevre): IEvenementChevre {
    const copy: IEvenementChevre = Object.assign({}, evenementChevre, {
      date: evenementChevre.date != null && evenementChevre.date.isValid() ? evenementChevre.date.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date != null ? moment(res.body.date) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((evenementChevre: IEvenementChevre) => {
        evenementChevre.date = evenementChevre.date != null ? moment(evenementChevre.date) : null;
      });
    }
    return res;
  }
}

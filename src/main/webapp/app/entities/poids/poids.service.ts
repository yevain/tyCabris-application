import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPoids } from 'app/shared/model/poids.model';

type EntityResponseType = HttpResponse<IPoids>;
type EntityArrayResponseType = HttpResponse<IPoids[]>;

@Injectable({ providedIn: 'root' })
export class PoidsService {
  public resourceUrl = SERVER_API_URL + 'api/poids';

  constructor(protected http: HttpClient) {}

  create(poids: IPoids): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(poids);
    return this.http
      .post<IPoids>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(poids: IPoids): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(poids);
    return this.http
      .put<IPoids>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPoids>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPoids[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(poids: IPoids): IPoids {
    const copy: IPoids = Object.assign({}, poids, {
      date: poids.date != null && poids.date.isValid() ? poids.date.format(DATE_FORMAT) : null
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
      res.body.forEach((poids: IPoids) => {
        poids.date = poids.date != null ? moment(poids.date) : null;
      });
    }
    return res;
  }
}

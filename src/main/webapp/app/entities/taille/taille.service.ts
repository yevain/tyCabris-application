import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITaille } from 'app/shared/model/taille.model';

type EntityResponseType = HttpResponse<ITaille>;
type EntityArrayResponseType = HttpResponse<ITaille[]>;

@Injectable({ providedIn: 'root' })
export class TailleService {
  public resourceUrl = SERVER_API_URL + 'api/tailles';

  constructor(protected http: HttpClient) {}

  create(taille: ITaille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taille);
    return this.http
      .post<ITaille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taille: ITaille): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taille);
    return this.http
      .put<ITaille>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaille>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaille[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taille: ITaille): ITaille {
    const copy: ITaille = Object.assign({}, taille, {
      date: taille.date != null && taille.date.isValid() ? taille.date.format(DATE_FORMAT) : null
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
      res.body.forEach((taille: ITaille) => {
        taille.date = taille.date != null ? moment(taille.date) : null;
      });
    }
    return res;
  }
}

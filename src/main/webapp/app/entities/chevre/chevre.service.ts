import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChevre } from 'app/shared/model/chevre.model';

type EntityResponseType = HttpResponse<IChevre>;
type EntityArrayResponseType = HttpResponse<IChevre[]>;

@Injectable({ providedIn: 'root' })
export class ChevreService {
  public resourceUrl = SERVER_API_URL + 'api/chevres';

  constructor(protected http: HttpClient) {}

  create(chevre: IChevre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chevre);
    return this.http
      .post<IChevre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chevre: IChevre): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chevre);
    return this.http
      .put<IChevre>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChevre>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChevre[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chevre: IChevre): IChevre {
    const copy: IChevre = Object.assign({}, chevre, {
      naissance: chevre.naissance != null && chevre.naissance.isValid() ? chevre.naissance.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.naissance = res.body.naissance != null ? moment(res.body.naissance) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chevre: IChevre) => {
        chevre.naissance = chevre.naissance != null ? moment(chevre.naissance) : null;
      });
    }
    return res;
  }
}

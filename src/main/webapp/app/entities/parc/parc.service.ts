import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParc } from 'app/shared/model/parc.model';

type EntityResponseType = HttpResponse<IParc>;
type EntityArrayResponseType = HttpResponse<IParc[]>;

@Injectable({ providedIn: 'root' })
export class ParcService {
  public resourceUrl = SERVER_API_URL + 'api/parcs';

  constructor(protected http: HttpClient) {}

  create(parc: IParc): Observable<EntityResponseType> {
    return this.http.post<IParc>(this.resourceUrl, parc, { observe: 'response' });
  }

  update(parc: IParc): Observable<EntityResponseType> {
    return this.http.put<IParc>(this.resourceUrl, parc, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParc>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParc[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEvenement } from 'app/shared/model/evenement.model';

type EntityResponseType = HttpResponse<IEvenement>;
type EntityArrayResponseType = HttpResponse<IEvenement[]>;

@Injectable({ providedIn: 'root' })
export class EvenementService {
  public resourceUrl = SERVER_API_URL + 'api/evenements';

  constructor(protected http: HttpClient) {}

  create(evenement: IEvenement): Observable<EntityResponseType> {
    return this.http.post<IEvenement>(this.resourceUrl, evenement, { observe: 'response' });
  }

  update(evenement: IEvenement): Observable<EntityResponseType> {
    return this.http.put<IEvenement>(this.resourceUrl, evenement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEvenement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEvenement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetailsFacture } from 'app/shared/model/details-facture.model';

type EntityResponseType = HttpResponse<IDetailsFacture>;
type EntityArrayResponseType = HttpResponse<IDetailsFacture[]>;

@Injectable({ providedIn: 'root' })
export class DetailsFactureService {
  public resourceUrl = SERVER_API_URL + 'api/details-factures';

  constructor(protected http: HttpClient) {}

  create(detailsFacture: IDetailsFacture): Observable<EntityResponseType> {
    return this.http.post<IDetailsFacture>(this.resourceUrl, detailsFacture, { observe: 'response' });
  }

  update(detailsFacture: IDetailsFacture): Observable<EntityResponseType> {
    return this.http.put<IDetailsFacture>(this.resourceUrl, detailsFacture, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailsFacture>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailsFacture[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  // AI code
  getAlDetailsFactureByFActureId(id: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailsFacture[]>(`${this.resourceUrl}/facture/${id}`, { params: options, observe: 'response' });
  }
}

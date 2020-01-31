import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDepense } from 'app/shared/model/depense.model';

type EntityResponseType = HttpResponse<IDepense>;
type EntityArrayResponseType = HttpResponse<IDepense[]>;

@Injectable({ providedIn: 'root' })
export class DepenseService {
  public resourceUrl = SERVER_API_URL + 'api/depenses';

  constructor(protected http: HttpClient) {}

  create(depense: IDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depense);
    return this.http
      .post<IDepense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(depense: IDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(depense);
    return this.http
      .put<IDepense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDepense>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDepense[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(depense: IDepense): IDepense {
    const copy: IDepense = Object.assign({}, depense, {
      dateFacturation: depense.dateFacturation != null && depense.dateFacturation.isValid() ? depense.dateFacturation.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateFacturation = res.body.dateFacturation != null ? moment(res.body.dateFacturation) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((depense: IDepense) => {
        depense.dateFacturation = depense.dateFacturation != null ? moment(depense.dateFacturation) : null;
      });
    }
    return res;
  }
}

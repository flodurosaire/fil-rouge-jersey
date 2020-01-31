import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeDepense } from 'app/shared/model/type-depense.model';

type EntityResponseType = HttpResponse<ITypeDepense>;
type EntityArrayResponseType = HttpResponse<ITypeDepense[]>;

@Injectable({ providedIn: 'root' })
export class TypeDepenseService {
  public resourceUrl = SERVER_API_URL + 'api/type-depenses';

  constructor(protected http: HttpClient) {}

  create(typeDepense: ITypeDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeDepense);
    return this.http
      .post<ITypeDepense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(typeDepense: ITypeDepense): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(typeDepense);
    return this.http
      .put<ITypeDepense>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITypeDepense>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITypeDepense[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(typeDepense: ITypeDepense): ITypeDepense {
    const copy: ITypeDepense = Object.assign({}, typeDepense, {
      dateFacturation:
        typeDepense.dateFacturation != null && typeDepense.dateFacturation.isValid() ? typeDepense.dateFacturation.toJSON() : null
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
      res.body.forEach((typeDepense: ITypeDepense) => {
        typeDepense.dateFacturation = typeDepense.dateFacturation != null ? moment(typeDepense.dateFacturation) : null;
      });
    }
    return res;
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { FactureService } from './facture.service';
import { IFacture } from 'app/shared/model/facture.model';
import { IDetailsFacture } from 'app/shared/model/details-facture.model';
import { DetailsFactureService } from './details-facture.service';

@Injectable({ providedIn: 'root' })
export class FactureCalculeService {
  public resourceUrl = SERVER_API_URL + 'api/factures';

  constructor(
    protected factureService: FactureService,
    protected detailsFactureService: DetailsFactureService,
    protected http: HttpClient
  ) {}

  // getTotalFacture(idFacture: number): any {
  //   let detailsFactures: IDetailsFacture[];
  //   let total: number = 0;
  //   this.detailsFactureService.getAlDetailsFactureByFActureId(idFacture,{})
  //   .subscribe(res=>{
  //     detailsFactures = res.body;
  //     detailsFactures.forEach((e:IDetailsFacture)=>{
  //       total= total+(e.qteProduit * e.produitPrix);
  //     });
  //     console.log("-----"+total);
  //     return total;
  //   });
  //   return total;
  // }
  getTotalFacture(idFacture: number): Observable<any> {
    let detailsFactures: IDetailsFacture[];
    let total = 0;
    const totalFactureObservable = new Observable(observer => {
      this.detailsFactureService.getAlDetailsFactureByFActureId(idFacture, {}).subscribe(res => {
        detailsFactures = res.body;
        detailsFactures.forEach((e: IDetailsFacture) => {
          total = total + e.qteProduit * e.produitPrix;
        });
        observer.next(total);
      });
    });
    return totalFactureObservable;
  }

  getTotalLineFacture(detailsFacture: IDetailsFacture) {}
}

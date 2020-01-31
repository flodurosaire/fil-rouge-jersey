import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFacture } from 'app/shared/model/facture.model';
import { HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { IDetailsFacture } from 'app/shared/model/details-facture.model';
import { ITEMS_PER_PAGE } from 'app/shared';
import { Subscription } from 'rxjs';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { FactureCalculeService } from './facture-calcule.service';
import { DetailsFactureService } from './details-facture.service';

@Component({
  selector: 'jhi-facture-detail',
  templateUrl: './facture-detail.component.html'
})
export class FactureDetailComponent implements OnInit {
  facture: IFacture;
  currentAccount: any;
  detailsFactures: IDetailsFacture[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;
  totalFacture: number;
  constructor(
    private detailsFactureService: DetailsFactureService,
    private factureCalculeService: FactureCalculeService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.totalFacture = 0;
    });
  }

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ facture }) => {
      this.facture = facture;
      this.loadAllDetailsFacture(this.facture.id);
    });
  }

  previousState() {
    window.history.back();
  }

  loadAllDetailsFacture(id: number) {
    this.detailsFactureService
      .getAlDetailsFactureByFActureId(this.facture.id, {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IDetailsFacture[]>) => this.paginateDetailsFactures(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.factureCalculeService.getTotalFacture(this.facture.id).subscribe(res => {
      this.totalFacture = res;
    });
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDetailsFactures(data: IDetailsFacture[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    // *** data */
    this.detailsFactures = data;
    console.log(data);
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  public deleteFactureDetail(id: number) {
    this.detailsFactureService.delete(id).subscribe(() => {
      this.loadAllDetailsFacture(this.facture.id);
    });
  }
}

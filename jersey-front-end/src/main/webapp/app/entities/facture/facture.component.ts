import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { IFacture } from 'app/shared/model/facture.model';
import { AccountService, Account, UserService } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { FactureService } from './facture.service';
import { FactureCalculeService } from './facture-calcule.service';

@Component({
  selector: 'jhi-facture',
  templateUrl: './facture.component.html',
  styleUrls: ['facture.scss']

})
export class FactureComponent implements OnInit, OnDestroy {
  currentAccount: Account;
  factures: IFacture[];
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

  constructor(
    protected factureService: FactureService,
    protected userService: UserService,
    protected factureCalculeService: FactureCalculeService,
    protected parseLinks: JhiParseLinks,
    protected jhiAlertService: JhiAlertService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  ngOnInit() {
    this.accountService.identity().then((account: Account) => {
      this.currentAccount = account;
      this.loadAll();
    });
    this.registerChangeInFactures();
  }

  loadAll() {
    if (this.currentAccount.authorities.indexOf('ROLE_ADMIN') > -1) {
      this.factureService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe(
          (res: HttpResponse<IFacture[]>) => this.paginateFactures(res.body, res.headers),
          (res: HttpErrorResponse) => this.onError(res.message)
        );
    } else {
      this.userService.find(this.currentAccount.login).subscribe(res => {
        this.factureService
          .findAllByClientId(res.body.id, {
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
          })
          .subscribe(
            (result: HttpResponse<IFacture[]>) => this.paginateFactures(result.body, result.headers),
            (result: HttpErrorResponse) => this.onError(result.message)
          );
      });
    }
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/facture'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/facture',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFacture) {
    return item.id;
  }

  registerChangeInFactures() {
    this.eventSubscriber = this.eventManager.subscribe('factureListModification', response => this.loadAll());
  }

  // original code
  // sort() {
  //   const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
  //   if (this.predicate !== 'id') {
  //     result.push('id');
  //   }
  //   return result;
  // }
  sort() {
    const result = [this.reverse ? 'asc' : 'desc'];
    // if (this.predicate !== 'id') {
    //   result.push('id');
    // }
    return result;
  }

  protected paginateFactures(data: IFacture[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.factures = data;
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

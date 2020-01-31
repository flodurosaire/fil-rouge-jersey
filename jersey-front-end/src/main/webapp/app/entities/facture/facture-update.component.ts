import { Component, OnInit, ViewChild, TemplateRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiParseLinks } from 'ng-jhipster';
import { IFacture, Facture } from 'app/shared/model/facture.model';
import { FactureService } from './facture.service';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from 'app/entities/client';
import { IDetailsFacture } from 'app/shared/model/details-facture.model';
import { Subscription } from 'rxjs';
import { ITEMS_PER_PAGE } from 'app/shared';
import { ProduitService } from '../produit';
import { IProduit } from 'app/shared/model/produit.model';
import { NgbModal, NgbModalRef, NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { DetailsFactureService } from './details-facture.service';

@Component({
  selector: 'jhi-facture-update',
  templateUrl: './facture-update.component.html'
})
export class FactureUpdateComponent implements OnInit {
  @ViewChild('template')
  private modalTemRef: NgbModalRef;

  facture: IFacture;
  isSaving: boolean;

  // add
  currentAccount: any;
  detailsFactures: IDetailsFacture[];
  detailFactureToSave: IDetailsFacture;
  detailFactureToAdd: IDetailsFacture;
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
  dfToDeleteId: number;

  clients: IClient[];
  factures: IFacture[];
  produits: IProduit[];
  selectedClient: IClient;

  rowToEditId: number;

  editForm = this.fb.group({
    id: [],
    dateFacturation: [null, [Validators.required]],
    clientId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected factureService: FactureService,
    protected parseLinks: JhiParseLinks,
    private detailsFactureService: DetailsFactureService,
    protected clientService: ClientService,
    protected produitService: ProduitService,
    private fb: FormBuilder,
    private _modalService: NgbModal,
    // public activeModal: NgbActiveModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.selectedClient = null;
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.resetDetailsFactureObjects();
    });
  }

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ facture }) => {
      this.updateForm(facture);
      this.facture = facture;
    });
    this.clientService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClient[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClient[]>) => response.body)
      )
      .subscribe((res: IClient[]) => (this.clients = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.loadFactureAndProduits();
    this.loadAllDetailsFacture(this.facture.id);
  }

  updateForm(facture: IFacture) {
    this.editForm.patchValue({
      id: facture.id,
      dateFacturation: facture.dateFacturation != null ? facture.dateFacturation.format(DATE_FORMAT) : null,
      clientId: facture.clientId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const facture = this.createFromForm();
    if (facture.id !== undefined) {
      this.subscribeToSaveResponse(this.factureService.update(facture));
    } else {
      this.subscribeToSaveResponse(this.factureService.create(facture));
    }
  }

  private createFromForm(): IFacture {
    const entity = {
      ...new Facture(),
      id: this.editForm.get(['id']).value,
      dateFacturation:
        this.editForm.get(['dateFacturation']).value != null
          ? moment(this.editForm.get(['dateFacturation']).value, DATE_FORMAT)
          : undefined,
      clientId: this.editForm.get(['clientId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacture>>) {
    result.subscribe((res: HttpResponse<IFacture>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackClientById(index: number, item: IClient) {
    return item.id;
  }
  onClientSelect() {
    const id: number = this.editForm.get(['clientId']).value;
    if (this.editForm.get(['clientId']).value !== null) {
      this.clientService
        .find(id)
        .pipe(
          filter((mayBeOk: HttpResponse<IClient>) => mayBeOk.ok),
          map((response: HttpResponse<IClient>) => response.body)
        )
        .subscribe((res: IClient) => {
          this.selectedClient = res;
        });
    } else {
      this.selectedClient = null;
    }
  }

  loadAllDetailsFacture(id: number) {
    if (this.facture.id) {
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
    }
  }
  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'desc' : 'asc')];
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

  public deleteFactureDetail(id: number) {
    this.detailsFactureService.delete(id).subscribe(() => {
      this.loadAllDetailsFacture(this.facture.id);
    });
  }

  setRowToEdit(id: number) {
    this.rowToEditId = id;
    this.detailFactureToSave = { id: null, factureId: null, produitId: null, qteProduit: null, description: null };
    this.detailsFactureService
      .find(id)
      .pipe(
        filter((mayBeOk: HttpResponse<IDetailsFacture>) => mayBeOk.ok),
        map((response: HttpResponse<IDetailsFacture>) => response.body)
      )
      .subscribe((res: IDetailsFacture) => {
        this.detailFactureToSave = res;
      });
  }

  loadFactureAndProduits() {
    // list facture
    this.factureService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFacture[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFacture[]>) => response.body)
      )
      .subscribe((res: IFacture[]) => (this.factures = res), (res: HttpErrorResponse) => this.onError(res.message));
    // list produits
    this.produitService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProduit[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProduit[]>) => response.body)
      )
      .subscribe((res: IProduit[]) => (this.produits = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  saveDetailsFacture(id: number) {
    this.rowToEditId = null;
    let detailsFacture = this.detailFactureToSave;
    if (detailsFacture.id !== undefined && detailsFacture.id !== null) {
      detailsFacture = this.detailFactureToSave;
      this.detailsFactureService.update(detailsFacture).subscribe(() => {
        this.loadAllDetailsFacture(this.facture.id);
      });
    } else {
      detailsFacture = this.detailFactureToAdd;
      detailsFacture.factureId = this.facture.id;
      this.detailsFactureService.create(detailsFacture).subscribe(() => {
        this.resetDetailsFactureObjects();
        this.loadAllDetailsFacture(this.facture.id);
      });
    }
    this.cancelEdit();
  }
  cancelEdit() {
    this.rowToEditId = null;
  }
  resetDetailsFactureObjects() {
    this.detailFactureToSave = { id: null, factureId: null, produitId: null, qteProduit: null, description: null };
    this.detailFactureToAdd = { id: null, factureId: null, produitId: null, qteProduit: null, description: null };
  }
  openModal(id?: number) {
    if (id !== null && id !== undefined) {
      this.dfToDeleteId = id;
      this._modalService.open(this.modalTemRef);
    }
  }
  closeModal() {
    this._modalService.dismissAll();
  }
  confirmDelete() {
    this.deleteFactureDetail(this.dfToDeleteId);
    this.closeModal();
  }
}

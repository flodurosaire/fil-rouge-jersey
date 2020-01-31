import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IDepense, Depense } from 'app/shared/model/depense.model';
import { DepenseService } from './depense.service';
import { ITypeDepense } from 'app/shared/model/type-depense.model';
import { TypeDepenseService } from 'app/entities/type-depense';

@Component({
  selector: 'jhi-depense-update',
  templateUrl: './depense-update.component.html'
})
export class DepenseUpdateComponent implements OnInit {
  depense: IDepense;
  isSaving: boolean;

  typedepenses: ITypeDepense[];

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    montant: [null, [Validators.required]],
    description: [],
    dateFacturation: [null, [Validators.required]],
    typeDepenseId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected depenseService: DepenseService,
    protected typeDepenseService: TypeDepenseService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ depense }) => {
      this.updateForm(depense);
      this.depense = depense;
    });
    this.typeDepenseService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITypeDepense[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITypeDepense[]>) => response.body)
      )
      .subscribe((res: ITypeDepense[]) => (this.typedepenses = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(depense: IDepense) {
    this.editForm.patchValue({
      id: depense.id,
      libelle: depense.libelle,
      montant: depense.montant,
      description: depense.description,
      dateFacturation: depense.dateFacturation != null ? depense.dateFacturation.format(DATE_TIME_FORMAT) : null,
      typeDepenseId: depense.typeDepenseId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const depense = this.createFromForm();
    if (depense.id !== undefined) {
      this.subscribeToSaveResponse(this.depenseService.update(depense));
    } else {
      this.subscribeToSaveResponse(this.depenseService.create(depense));
    }
  }

  private createFromForm(): IDepense {
    const entity = {
      ...new Depense(),
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value,
      montant: this.editForm.get(['montant']).value,
      description: this.editForm.get(['description']).value,
      dateFacturation:
        this.editForm.get(['dateFacturation']).value != null
          ? moment(this.editForm.get(['dateFacturation']).value, DATE_TIME_FORMAT)
          : undefined,
      typeDepenseId: this.editForm.get(['typeDepenseId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepense>>) {
    result.subscribe((res: HttpResponse<IDepense>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackTypeDepenseById(index: number, item: ITypeDepense) {
    return item.id;
  }
}

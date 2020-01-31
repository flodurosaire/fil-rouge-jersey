import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ITypeDepense, TypeDepense } from 'app/shared/model/type-depense.model';
import { TypeDepenseService } from './type-depense.service';

@Component({
  selector: 'jhi-type-depense-update',
  templateUrl: './type-depense-update.component.html'
})
export class TypeDepenseUpdateComponent implements OnInit {
  typeDepense: ITypeDepense;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    libelle: [null, [Validators.required]],
    description: [],
    dateFacturation: []
  });

  constructor(protected typeDepenseService: TypeDepenseService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ typeDepense }) => {
      this.updateForm(typeDepense);
      this.typeDepense = typeDepense;
    });
  }

  updateForm(typeDepense: ITypeDepense) {
    this.editForm.patchValue({
      id: typeDepense.id,
      libelle: typeDepense.libelle,
      description: typeDepense.description,
      dateFacturation: typeDepense.dateFacturation != null ? typeDepense.dateFacturation.format(DATE_TIME_FORMAT) : null
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const typeDepense = this.createFromForm();
    if (typeDepense.id !== undefined) {
      this.subscribeToSaveResponse(this.typeDepenseService.update(typeDepense));
    } else {
      this.subscribeToSaveResponse(this.typeDepenseService.create(typeDepense));
    }
  }

  private createFromForm(): ITypeDepense {
    const entity = {
      ...new TypeDepense(),
      id: this.editForm.get(['id']).value,
      libelle: this.editForm.get(['libelle']).value,
      description: this.editForm.get(['description']).value,
      dateFacturation:
        this.editForm.get(['dateFacturation']).value != null
          ? moment(this.editForm.get(['dateFacturation']).value, DATE_TIME_FORMAT)
          : undefined
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeDepense>>) {
    result.subscribe((res: HttpResponse<ITypeDepense>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

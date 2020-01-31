import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeDepense } from 'app/shared/model/type-depense.model';
import { TypeDepenseService } from './type-depense.service';

@Component({
  selector: 'jhi-type-depense-delete-dialog',
  templateUrl: './type-depense-delete-dialog.component.html'
})
export class TypeDepenseDeleteDialogComponent {
  typeDepense: ITypeDepense;

  constructor(
    protected typeDepenseService: TypeDepenseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.typeDepenseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'typeDepenseListModification',
        content: 'Deleted an typeDepense'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-type-depense-delete-popup',
  template: ''
})
export class TypeDepenseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ typeDepense }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TypeDepenseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.typeDepense = typeDepense;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/type-depense', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/type-depense', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

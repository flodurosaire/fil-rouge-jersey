import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepense } from 'app/shared/model/depense.model';
import { DepenseService } from './depense.service';

@Component({
  selector: 'jhi-depense-delete-dialog',
  templateUrl: './depense-delete-dialog.component.html'
})
export class DepenseDeleteDialogComponent {
  depense: IDepense;

  constructor(protected depenseService: DepenseService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.depenseService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'depenseListModification',
        content: 'Deleted an depense'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-depense-delete-popup',
  template: ''
})
export class DepenseDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ depense }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DepenseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.depense = depense;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/depense', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/depense', { outlets: { popup: null } }]);
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

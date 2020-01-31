import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDepense } from 'app/shared/model/depense.model';

@Component({
  selector: 'jhi-depense-detail',
  templateUrl: './depense-detail.component.html'
})
export class DepenseDetailComponent implements OnInit {
  depense: IDepense;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ depense }) => {
      this.depense = depense;
    });
  }

  previousState() {
    window.history.back();
  }
}

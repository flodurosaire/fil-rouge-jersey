import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeDepense } from 'app/shared/model/type-depense.model';
import { TypeDepenseService } from './type-depense.service';
import { TypeDepenseComponent } from './type-depense.component';
import { TypeDepenseDetailComponent } from './type-depense-detail.component';
import { TypeDepenseUpdateComponent } from './type-depense-update.component';
import { TypeDepenseDeletePopupComponent } from './type-depense-delete-dialog.component';
import { ITypeDepense } from 'app/shared/model/type-depense.model';

@Injectable({ providedIn: 'root' })
export class TypeDepenseResolve implements Resolve<ITypeDepense> {
  constructor(private service: TypeDepenseService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeDepense> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TypeDepense>) => response.ok),
        map((typeDepense: HttpResponse<TypeDepense>) => typeDepense.body)
      );
    }
    return of(new TypeDepense());
  }
}

export const typeDepenseRoute: Routes = [
  {
    path: '',
    component: TypeDepenseComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      defaultSort: 'id,asc',
      pageTitle: 'TypeDepenses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypeDepenseDetailComponent,
    resolve: {
      typeDepense: TypeDepenseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'TypeDepenses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypeDepenseUpdateComponent,
    resolve: {
      typeDepense: TypeDepenseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'TypeDepenses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypeDepenseUpdateComponent,
    resolve: {
      typeDepense: TypeDepenseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'TypeDepenses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const typeDepensePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TypeDepenseDeletePopupComponent,
    resolve: {
      typeDepense: TypeDepenseResolve
    },
    data: {
      authorities: ['ROLE_ADMIN'],
      pageTitle: 'TypeDepenses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

import { Route } from '@angular/router';

import { DashboardComponent } from './';

export const HOME_ROUTE: Route = {
  path: 'dashboard',
  component: DashboardComponent,
  data: {
    authorities: ['ROLE_ADMIN'],
    pageTitle: 'dashboard'
  }
};

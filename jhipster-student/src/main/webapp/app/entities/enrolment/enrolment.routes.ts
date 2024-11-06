import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import EnrolmentResolve from './route/enrolment-routing-resolve.service';

const enrolmentRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/enrolment.component').then(m => m.EnrolmentComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/enrolment-detail.component').then(m => m.EnrolmentDetailComponent),
    resolve: {
      enrolment: EnrolmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/enrolment-update.component').then(m => m.EnrolmentUpdateComponent),
    resolve: {
      enrolment: EnrolmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/enrolment-update.component').then(m => m.EnrolmentUpdateComponent),
    resolve: {
      enrolment: EnrolmentResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default enrolmentRoute;

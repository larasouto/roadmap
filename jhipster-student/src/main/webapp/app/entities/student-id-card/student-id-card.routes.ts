import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import StudentIdCardResolve from './route/student-id-card-routing-resolve.service';

const studentIdCardRoute: Routes = [
  {
    path: '',
    loadComponent: () => import('./list/student-id-card.component').then(m => m.StudentIdCardComponent),
    data: {
      defaultSort: `id,${ASC}`,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    loadComponent: () => import('./detail/student-id-card-detail.component').then(m => m.StudentIdCardDetailComponent),
    resolve: {
      studentIdCard: StudentIdCardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    loadComponent: () => import('./update/student-id-card-update.component').then(m => m.StudentIdCardUpdateComponent),
    resolve: {
      studentIdCard: StudentIdCardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./update/student-id-card-update.component').then(m => m.StudentIdCardUpdateComponent),
    resolve: {
      studentIdCard: StudentIdCardResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default studentIdCardRoute;

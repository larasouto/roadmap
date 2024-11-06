import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnrolment } from '../enrolment.model';
import { EnrolmentService } from '../service/enrolment.service';

const enrolmentResolve = (route: ActivatedRouteSnapshot): Observable<null | IEnrolment> => {
  const id = route.params.id;
  if (id) {
    return inject(EnrolmentService)
      .find(id)
      .pipe(
        mergeMap((enrolment: HttpResponse<IEnrolment>) => {
          if (enrolment.body) {
            return of(enrolment.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default enrolmentResolve;

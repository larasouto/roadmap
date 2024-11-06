import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStudentIdCard } from '../student-id-card.model';
import { StudentIdCardService } from '../service/student-id-card.service';

const studentIdCardResolve = (route: ActivatedRouteSnapshot): Observable<null | IStudentIdCard> => {
  const id = route.params.id;
  if (id) {
    return inject(StudentIdCardService)
      .find(id)
      .pipe(
        mergeMap((studentIdCard: HttpResponse<IStudentIdCard>) => {
          if (studentIdCard.body) {
            return of(studentIdCard.body);
          }
          inject(Router).navigate(['404']);
          return EMPTY;
        }),
      );
  }
  return of(null);
};

export default studentIdCardResolve;

import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, map } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnrolment, NewEnrolment } from '../enrolment.model';

export type PartialUpdateEnrolment = Partial<IEnrolment> & Pick<IEnrolment, 'id'>;

type RestOf<T extends IEnrolment | NewEnrolment> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

export type RestEnrolment = RestOf<IEnrolment>;

export type NewRestEnrolment = RestOf<NewEnrolment>;

export type PartialUpdateRestEnrolment = RestOf<PartialUpdateEnrolment>;

export type EntityResponseType = HttpResponse<IEnrolment>;
export type EntityArrayResponseType = HttpResponse<IEnrolment[]>;

@Injectable({ providedIn: 'root' })
export class EnrolmentService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/enrolments');

  create(enrolment: NewEnrolment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enrolment);
    return this.http
      .post<RestEnrolment>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(enrolment: IEnrolment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enrolment);
    return this.http
      .put<RestEnrolment>(`${this.resourceUrl}/${this.getEnrolmentIdentifier(enrolment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(enrolment: PartialUpdateEnrolment): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(enrolment);
    return this.http
      .patch<RestEnrolment>(`${this.resourceUrl}/${this.getEnrolmentIdentifier(enrolment)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEnrolment>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEnrolment[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEnrolmentIdentifier(enrolment: Pick<IEnrolment, 'id'>): number {
    return enrolment.id;
  }

  compareEnrolment(o1: Pick<IEnrolment, 'id'> | null, o2: Pick<IEnrolment, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnrolmentIdentifier(o1) === this.getEnrolmentIdentifier(o2) : o1 === o2;
  }

  addEnrolmentToCollectionIfMissing<Type extends Pick<IEnrolment, 'id'>>(
    enrolmentCollection: Type[],
    ...enrolmentsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enrolments: Type[] = enrolmentsToCheck.filter(isPresent);
    if (enrolments.length > 0) {
      const enrolmentCollectionIdentifiers = enrolmentCollection.map(enrolmentItem => this.getEnrolmentIdentifier(enrolmentItem));
      const enrolmentsToAdd = enrolments.filter(enrolmentItem => {
        const enrolmentIdentifier = this.getEnrolmentIdentifier(enrolmentItem);
        if (enrolmentCollectionIdentifiers.includes(enrolmentIdentifier)) {
          return false;
        }
        enrolmentCollectionIdentifiers.push(enrolmentIdentifier);
        return true;
      });
      return [...enrolmentsToAdd, ...enrolmentCollection];
    }
    return enrolmentCollection;
  }

  protected convertDateFromClient<T extends IEnrolment | NewEnrolment | PartialUpdateEnrolment>(enrolment: T): RestOf<T> {
    return {
      ...enrolment,
      createdAt: enrolment.createdAt?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEnrolment: RestEnrolment): IEnrolment {
    return {
      ...restEnrolment,
      createdAt: restEnrolment.createdAt ? dayjs(restEnrolment.createdAt) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEnrolment>): HttpResponse<IEnrolment> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEnrolment[]>): HttpResponse<IEnrolment[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}

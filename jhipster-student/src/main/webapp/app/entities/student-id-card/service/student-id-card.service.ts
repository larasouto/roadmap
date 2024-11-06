import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStudentIdCard, NewStudentIdCard } from '../student-id-card.model';

export type PartialUpdateStudentIdCard = Partial<IStudentIdCard> & Pick<IStudentIdCard, 'id'>;

export type EntityResponseType = HttpResponse<IStudentIdCard>;
export type EntityArrayResponseType = HttpResponse<IStudentIdCard[]>;

@Injectable({ providedIn: 'root' })
export class StudentIdCardService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/student-id-cards');

  create(studentIdCard: NewStudentIdCard): Observable<EntityResponseType> {
    return this.http.post<IStudentIdCard>(this.resourceUrl, studentIdCard, { observe: 'response' });
  }

  update(studentIdCard: IStudentIdCard): Observable<EntityResponseType> {
    return this.http.put<IStudentIdCard>(`${this.resourceUrl}/${this.getStudentIdCardIdentifier(studentIdCard)}`, studentIdCard, {
      observe: 'response',
    });
  }

  partialUpdate(studentIdCard: PartialUpdateStudentIdCard): Observable<EntityResponseType> {
    return this.http.patch<IStudentIdCard>(`${this.resourceUrl}/${this.getStudentIdCardIdentifier(studentIdCard)}`, studentIdCard, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStudentIdCard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStudentIdCard[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getStudentIdCardIdentifier(studentIdCard: Pick<IStudentIdCard, 'id'>): number {
    return studentIdCard.id;
  }

  compareStudentIdCard(o1: Pick<IStudentIdCard, 'id'> | null, o2: Pick<IStudentIdCard, 'id'> | null): boolean {
    return o1 && o2 ? this.getStudentIdCardIdentifier(o1) === this.getStudentIdCardIdentifier(o2) : o1 === o2;
  }

  addStudentIdCardToCollectionIfMissing<Type extends Pick<IStudentIdCard, 'id'>>(
    studentIdCardCollection: Type[],
    ...studentIdCardsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const studentIdCards: Type[] = studentIdCardsToCheck.filter(isPresent);
    if (studentIdCards.length > 0) {
      const studentIdCardCollectionIdentifiers = studentIdCardCollection.map(studentIdCardItem =>
        this.getStudentIdCardIdentifier(studentIdCardItem),
      );
      const studentIdCardsToAdd = studentIdCards.filter(studentIdCardItem => {
        const studentIdCardIdentifier = this.getStudentIdCardIdentifier(studentIdCardItem);
        if (studentIdCardCollectionIdentifiers.includes(studentIdCardIdentifier)) {
          return false;
        }
        studentIdCardCollectionIdentifiers.push(studentIdCardIdentifier);
        return true;
      });
      return [...studentIdCardsToAdd, ...studentIdCardCollection];
    }
    return studentIdCardCollection;
  }
}

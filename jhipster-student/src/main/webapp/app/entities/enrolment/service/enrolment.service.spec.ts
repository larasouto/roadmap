import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEnrolment } from '../enrolment.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../enrolment.test-samples';

import { EnrolmentService, RestEnrolment } from './enrolment.service';

const requireRestSample: RestEnrolment = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
};

describe('Enrolment Service', () => {
  let service: EnrolmentService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnrolment | IEnrolment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EnrolmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Enrolment', () => {
      const enrolment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enrolment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Enrolment', () => {
      const enrolment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enrolment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Enrolment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Enrolment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Enrolment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnrolmentToCollectionIfMissing', () => {
      it('should add a Enrolment to an empty array', () => {
        const enrolment: IEnrolment = sampleWithRequiredData;
        expectedResult = service.addEnrolmentToCollectionIfMissing([], enrolment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enrolment);
      });

      it('should not add a Enrolment to an array that contains it', () => {
        const enrolment: IEnrolment = sampleWithRequiredData;
        const enrolmentCollection: IEnrolment[] = [
          {
            ...enrolment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnrolmentToCollectionIfMissing(enrolmentCollection, enrolment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Enrolment to an array that doesn't contain it", () => {
        const enrolment: IEnrolment = sampleWithRequiredData;
        const enrolmentCollection: IEnrolment[] = [sampleWithPartialData];
        expectedResult = service.addEnrolmentToCollectionIfMissing(enrolmentCollection, enrolment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enrolment);
      });

      it('should add only unique Enrolment to an array', () => {
        const enrolmentArray: IEnrolment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enrolmentCollection: IEnrolment[] = [sampleWithRequiredData];
        expectedResult = service.addEnrolmentToCollectionIfMissing(enrolmentCollection, ...enrolmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enrolment: IEnrolment = sampleWithRequiredData;
        const enrolment2: IEnrolment = sampleWithPartialData;
        expectedResult = service.addEnrolmentToCollectionIfMissing([], enrolment, enrolment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enrolment);
        expect(expectedResult).toContain(enrolment2);
      });

      it('should accept null and undefined values', () => {
        const enrolment: IEnrolment = sampleWithRequiredData;
        expectedResult = service.addEnrolmentToCollectionIfMissing([], null, enrolment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enrolment);
      });

      it('should return initial array if no Enrolment is added', () => {
        const enrolmentCollection: IEnrolment[] = [sampleWithRequiredData];
        expectedResult = service.addEnrolmentToCollectionIfMissing(enrolmentCollection, undefined, null);
        expect(expectedResult).toEqual(enrolmentCollection);
      });
    });

    describe('compareEnrolment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnrolment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEnrolment(entity1, entity2);
        const compareResult2 = service.compareEnrolment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEnrolment(entity1, entity2);
        const compareResult2 = service.compareEnrolment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEnrolment(entity1, entity2);
        const compareResult2 = service.compareEnrolment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IStudentIdCard } from '../student-id-card.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../student-id-card.test-samples';

import { StudentIdCardService } from './student-id-card.service';

const requireRestSample: IStudentIdCard = {
  ...sampleWithRequiredData,
};

describe('StudentIdCard Service', () => {
  let service: StudentIdCardService;
  let httpMock: HttpTestingController;
  let expectedResult: IStudentIdCard | IStudentIdCard[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(StudentIdCardService);
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

    it('should create a StudentIdCard', () => {
      const studentIdCard = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(studentIdCard).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a StudentIdCard', () => {
      const studentIdCard = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(studentIdCard).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a StudentIdCard', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of StudentIdCard', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a StudentIdCard', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addStudentIdCardToCollectionIfMissing', () => {
      it('should add a StudentIdCard to an empty array', () => {
        const studentIdCard: IStudentIdCard = sampleWithRequiredData;
        expectedResult = service.addStudentIdCardToCollectionIfMissing([], studentIdCard);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(studentIdCard);
      });

      it('should not add a StudentIdCard to an array that contains it', () => {
        const studentIdCard: IStudentIdCard = sampleWithRequiredData;
        const studentIdCardCollection: IStudentIdCard[] = [
          {
            ...studentIdCard,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addStudentIdCardToCollectionIfMissing(studentIdCardCollection, studentIdCard);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a StudentIdCard to an array that doesn't contain it", () => {
        const studentIdCard: IStudentIdCard = sampleWithRequiredData;
        const studentIdCardCollection: IStudentIdCard[] = [sampleWithPartialData];
        expectedResult = service.addStudentIdCardToCollectionIfMissing(studentIdCardCollection, studentIdCard);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(studentIdCard);
      });

      it('should add only unique StudentIdCard to an array', () => {
        const studentIdCardArray: IStudentIdCard[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const studentIdCardCollection: IStudentIdCard[] = [sampleWithRequiredData];
        expectedResult = service.addStudentIdCardToCollectionIfMissing(studentIdCardCollection, ...studentIdCardArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const studentIdCard: IStudentIdCard = sampleWithRequiredData;
        const studentIdCard2: IStudentIdCard = sampleWithPartialData;
        expectedResult = service.addStudentIdCardToCollectionIfMissing([], studentIdCard, studentIdCard2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(studentIdCard);
        expect(expectedResult).toContain(studentIdCard2);
      });

      it('should accept null and undefined values', () => {
        const studentIdCard: IStudentIdCard = sampleWithRequiredData;
        expectedResult = service.addStudentIdCardToCollectionIfMissing([], null, studentIdCard, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(studentIdCard);
      });

      it('should return initial array if no StudentIdCard is added', () => {
        const studentIdCardCollection: IStudentIdCard[] = [sampleWithRequiredData];
        expectedResult = service.addStudentIdCardToCollectionIfMissing(studentIdCardCollection, undefined, null);
        expect(expectedResult).toEqual(studentIdCardCollection);
      });
    });

    describe('compareStudentIdCard', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareStudentIdCard(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareStudentIdCard(entity1, entity2);
        const compareResult2 = service.compareStudentIdCard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareStudentIdCard(entity1, entity2);
        const compareResult2 = service.compareStudentIdCard(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareStudentIdCard(entity1, entity2);
        const compareResult2 = service.compareStudentIdCard(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

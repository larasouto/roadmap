import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../student-id-card.test-samples';

import { StudentIdCardFormService } from './student-id-card-form.service';

describe('StudentIdCard Form Service', () => {
  let service: StudentIdCardFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentIdCardFormService);
  });

  describe('Service methods', () => {
    describe('createStudentIdCardFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createStudentIdCardFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cardNumber: expect.any(Object),
          }),
        );
      });

      it('passing IStudentIdCard should create a new form with FormGroup', () => {
        const formGroup = service.createStudentIdCardFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            cardNumber: expect.any(Object),
          }),
        );
      });
    });

    describe('getStudentIdCard', () => {
      it('should return NewStudentIdCard for default StudentIdCard initial value', () => {
        const formGroup = service.createStudentIdCardFormGroup(sampleWithNewData);

        const studentIdCard = service.getStudentIdCard(formGroup) as any;

        expect(studentIdCard).toMatchObject(sampleWithNewData);
      });

      it('should return NewStudentIdCard for empty StudentIdCard initial value', () => {
        const formGroup = service.createStudentIdCardFormGroup();

        const studentIdCard = service.getStudentIdCard(formGroup) as any;

        expect(studentIdCard).toMatchObject({});
      });

      it('should return IStudentIdCard', () => {
        const formGroup = service.createStudentIdCardFormGroup(sampleWithRequiredData);

        const studentIdCard = service.getStudentIdCard(formGroup) as any;

        expect(studentIdCard).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IStudentIdCard should not enable id FormControl', () => {
        const formGroup = service.createStudentIdCardFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewStudentIdCard should disable id FormControl', () => {
        const formGroup = service.createStudentIdCardFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

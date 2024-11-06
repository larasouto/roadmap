import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../enrolment.test-samples';

import { EnrolmentFormService } from './enrolment-form.service';

describe('Enrolment Form Service', () => {
  let service: EnrolmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EnrolmentFormService);
  });

  describe('Service methods', () => {
    describe('createEnrolmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEnrolmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            course: expect.any(Object),
            student: expect.any(Object),
          }),
        );
      });

      it('passing IEnrolment should create a new form with FormGroup', () => {
        const formGroup = service.createEnrolmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            createdAt: expect.any(Object),
            course: expect.any(Object),
            student: expect.any(Object),
          }),
        );
      });
    });

    describe('getEnrolment', () => {
      it('should return NewEnrolment for default Enrolment initial value', () => {
        const formGroup = service.createEnrolmentFormGroup(sampleWithNewData);

        const enrolment = service.getEnrolment(formGroup) as any;

        expect(enrolment).toMatchObject(sampleWithNewData);
      });

      it('should return NewEnrolment for empty Enrolment initial value', () => {
        const formGroup = service.createEnrolmentFormGroup();

        const enrolment = service.getEnrolment(formGroup) as any;

        expect(enrolment).toMatchObject({});
      });

      it('should return IEnrolment', () => {
        const formGroup = service.createEnrolmentFormGroup(sampleWithRequiredData);

        const enrolment = service.getEnrolment(formGroup) as any;

        expect(enrolment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEnrolment should not enable id FormControl', () => {
        const formGroup = service.createEnrolmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEnrolment should disable id FormControl', () => {
        const formGroup = service.createEnrolmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

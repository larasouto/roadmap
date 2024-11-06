import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEnrolment, NewEnrolment } from '../enrolment.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEnrolment for edit and NewEnrolmentFormGroupInput for create.
 */
type EnrolmentFormGroupInput = IEnrolment | PartialWithRequiredKeyOf<NewEnrolment>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEnrolment | NewEnrolment> = Omit<T, 'createdAt'> & {
  createdAt?: string | null;
};

type EnrolmentFormRawValue = FormValueOf<IEnrolment>;

type NewEnrolmentFormRawValue = FormValueOf<NewEnrolment>;

type EnrolmentFormDefaults = Pick<NewEnrolment, 'id' | 'createdAt'>;

type EnrolmentFormGroupContent = {
  id: FormControl<EnrolmentFormRawValue['id'] | NewEnrolment['id']>;
  createdAt: FormControl<EnrolmentFormRawValue['createdAt']>;
  course: FormControl<EnrolmentFormRawValue['course']>;
  student: FormControl<EnrolmentFormRawValue['student']>;
};

export type EnrolmentFormGroup = FormGroup<EnrolmentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EnrolmentFormService {
  createEnrolmentFormGroup(enrolment: EnrolmentFormGroupInput = { id: null }): EnrolmentFormGroup {
    const enrolmentRawValue = this.convertEnrolmentToEnrolmentRawValue({
      ...this.getFormDefaults(),
      ...enrolment,
    });
    return new FormGroup<EnrolmentFormGroupContent>({
      id: new FormControl(
        { value: enrolmentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      createdAt: new FormControl(enrolmentRawValue.createdAt, {
        validators: [Validators.required],
      }),
      course: new FormControl(enrolmentRawValue.course),
      student: new FormControl(enrolmentRawValue.student),
    });
  }

  getEnrolment(form: EnrolmentFormGroup): IEnrolment | NewEnrolment {
    return this.convertEnrolmentRawValueToEnrolment(form.getRawValue() as EnrolmentFormRawValue | NewEnrolmentFormRawValue);
  }

  resetForm(form: EnrolmentFormGroup, enrolment: EnrolmentFormGroupInput): void {
    const enrolmentRawValue = this.convertEnrolmentToEnrolmentRawValue({ ...this.getFormDefaults(), ...enrolment });
    form.reset(
      {
        ...enrolmentRawValue,
        id: { value: enrolmentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EnrolmentFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      createdAt: currentTime,
    };
  }

  private convertEnrolmentRawValueToEnrolment(rawEnrolment: EnrolmentFormRawValue | NewEnrolmentFormRawValue): IEnrolment | NewEnrolment {
    return {
      ...rawEnrolment,
      createdAt: dayjs(rawEnrolment.createdAt, DATE_TIME_FORMAT),
    };
  }

  private convertEnrolmentToEnrolmentRawValue(
    enrolment: IEnrolment | (Partial<NewEnrolment> & EnrolmentFormDefaults),
  ): EnrolmentFormRawValue | PartialWithRequiredKeyOf<NewEnrolmentFormRawValue> {
    return {
      ...enrolment,
      createdAt: enrolment.createdAt ? enrolment.createdAt.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}

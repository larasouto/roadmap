import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IStudentIdCard, NewStudentIdCard } from '../student-id-card.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStudentIdCard for edit and NewStudentIdCardFormGroupInput for create.
 */
type StudentIdCardFormGroupInput = IStudentIdCard | PartialWithRequiredKeyOf<NewStudentIdCard>;

type StudentIdCardFormDefaults = Pick<NewStudentIdCard, 'id'>;

type StudentIdCardFormGroupContent = {
  id: FormControl<IStudentIdCard['id'] | NewStudentIdCard['id']>;
  cardNumber: FormControl<IStudentIdCard['cardNumber']>;
};

export type StudentIdCardFormGroup = FormGroup<StudentIdCardFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StudentIdCardFormService {
  createStudentIdCardFormGroup(studentIdCard: StudentIdCardFormGroupInput = { id: null }): StudentIdCardFormGroup {
    const studentIdCardRawValue = {
      ...this.getFormDefaults(),
      ...studentIdCard,
    };
    return new FormGroup<StudentIdCardFormGroupContent>({
      id: new FormControl(
        { value: studentIdCardRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cardNumber: new FormControl(studentIdCardRawValue.cardNumber, {
        validators: [Validators.required, Validators.minLength(15), Validators.maxLength(15)],
      }),
    });
  }

  getStudentIdCard(form: StudentIdCardFormGroup): IStudentIdCard | NewStudentIdCard {
    return form.getRawValue() as IStudentIdCard | NewStudentIdCard;
  }

  resetForm(form: StudentIdCardFormGroup, studentIdCard: StudentIdCardFormGroupInput): void {
    const studentIdCardRawValue = { ...this.getFormDefaults(), ...studentIdCard };
    form.reset(
      {
        ...studentIdCardRawValue,
        id: { value: studentIdCardRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StudentIdCardFormDefaults {
    return {
      id: null,
    };
  }
}

import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IStudent, NewStudent } from '../student.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IStudent for edit and NewStudentFormGroupInput for create.
 */
type StudentFormGroupInput = IStudent | PartialWithRequiredKeyOf<NewStudent>;

type StudentFormDefaults = Pick<NewStudent, 'id'>;

type StudentFormGroupContent = {
  id: FormControl<IStudent['id'] | NewStudent['id']>;
  firstName: FormControl<IStudent['firstName']>;
  lastName: FormControl<IStudent['lastName']>;
  email: FormControl<IStudent['email']>;
  age: FormControl<IStudent['age']>;
  studentIdCard: FormControl<IStudent['studentIdCard']>;
};

export type StudentFormGroup = FormGroup<StudentFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class StudentFormService {
  createStudentFormGroup(student: StudentFormGroupInput = { id: null }): StudentFormGroup {
    const studentRawValue = {
      ...this.getFormDefaults(),
      ...student,
    };
    return new FormGroup<StudentFormGroupContent>({
      id: new FormControl(
        { value: studentRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      firstName: new FormControl(studentRawValue.firstName, {
        validators: [Validators.required, Validators.minLength(2)],
      }),
      lastName: new FormControl(studentRawValue.lastName, {
        validators: [Validators.required, Validators.minLength(2)],
      }),
      email: new FormControl(studentRawValue.email, {
        validators: [Validators.required, Validators.minLength(3)],
      }),
      age: new FormControl(studentRawValue.age, {
        validators: [Validators.required],
      }),
      studentIdCard: new FormControl(studentRawValue.studentIdCard),
    });
  }

  getStudent(form: StudentFormGroup): IStudent | NewStudent {
    return form.getRawValue() as IStudent | NewStudent;
  }

  resetForm(form: StudentFormGroup, student: StudentFormGroupInput): void {
    const studentRawValue = { ...this.getFormDefaults(), ...student };
    form.reset(
      {
        ...studentRawValue,
        id: { value: studentRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): StudentFormDefaults {
    return {
      id: null,
    };
  }
}

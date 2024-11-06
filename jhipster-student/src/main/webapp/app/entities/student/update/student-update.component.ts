import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStudentIdCard } from 'app/entities/student-id-card/student-id-card.model';
import { StudentIdCardService } from 'app/entities/student-id-card/service/student-id-card.service';
import { IStudent } from '../student.model';
import { StudentService } from '../service/student.service';
import { StudentFormGroup, StudentFormService } from './student-form.service';

@Component({
  standalone: true,
  selector: 'jhi-student-update',
  templateUrl: './student-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StudentUpdateComponent implements OnInit {
  isSaving = false;
  student: IStudent | null = null;

  studentIdCardsCollection: IStudentIdCard[] = [];

  protected studentService = inject(StudentService);
  protected studentFormService = inject(StudentFormService);
  protected studentIdCardService = inject(StudentIdCardService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StudentFormGroup = this.studentFormService.createStudentFormGroup();

  compareStudentIdCard = (o1: IStudentIdCard | null, o2: IStudentIdCard | null): boolean =>
    this.studentIdCardService.compareStudentIdCard(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ student }) => {
      this.student = student;
      if (student) {
        this.updateForm(student);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const student = this.studentFormService.getStudent(this.editForm);
    if (student.id !== null) {
      this.subscribeToSaveResponse(this.studentService.update(student));
    } else {
      this.subscribeToSaveResponse(this.studentService.create(student));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(student: IStudent): void {
    this.student = student;
    this.studentFormService.resetForm(this.editForm, student);

    this.studentIdCardsCollection = this.studentIdCardService.addStudentIdCardToCollectionIfMissing<IStudentIdCard>(
      this.studentIdCardsCollection,
      student.studentIdCard,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.studentIdCardService
      .query({ filter: 'student-is-null' })
      .pipe(map((res: HttpResponse<IStudentIdCard[]>) => res.body ?? []))
      .pipe(
        map((studentIdCards: IStudentIdCard[]) =>
          this.studentIdCardService.addStudentIdCardToCollectionIfMissing<IStudentIdCard>(studentIdCards, this.student?.studentIdCard),
        ),
      )
      .subscribe((studentIdCards: IStudentIdCard[]) => (this.studentIdCardsCollection = studentIdCards));
  }
}

import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICourse } from 'app/entities/course/course.model';
import { CourseService } from 'app/entities/course/service/course.service';
import { IStudent } from 'app/entities/student/student.model';
import { StudentService } from 'app/entities/student/service/student.service';
import { EnrolmentService } from '../service/enrolment.service';
import { IEnrolment } from '../enrolment.model';
import { EnrolmentFormGroup, EnrolmentFormService } from './enrolment-form.service';

@Component({
  standalone: true,
  selector: 'jhi-enrolment-update',
  templateUrl: './enrolment-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EnrolmentUpdateComponent implements OnInit {
  isSaving = false;
  enrolment: IEnrolment | null = null;

  coursesSharedCollection: ICourse[] = [];
  studentsSharedCollection: IStudent[] = [];

  protected enrolmentService = inject(EnrolmentService);
  protected enrolmentFormService = inject(EnrolmentFormService);
  protected courseService = inject(CourseService);
  protected studentService = inject(StudentService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EnrolmentFormGroup = this.enrolmentFormService.createEnrolmentFormGroup();

  compareCourse = (o1: ICourse | null, o2: ICourse | null): boolean => this.courseService.compareCourse(o1, o2);

  compareStudent = (o1: IStudent | null, o2: IStudent | null): boolean => this.studentService.compareStudent(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ enrolment }) => {
      this.enrolment = enrolment;
      if (enrolment) {
        this.updateForm(enrolment);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const enrolment = this.enrolmentFormService.getEnrolment(this.editForm);
    if (enrolment.id !== null) {
      this.subscribeToSaveResponse(this.enrolmentService.update(enrolment));
    } else {
      this.subscribeToSaveResponse(this.enrolmentService.create(enrolment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEnrolment>>): void {
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

  protected updateForm(enrolment: IEnrolment): void {
    this.enrolment = enrolment;
    this.enrolmentFormService.resetForm(this.editForm, enrolment);

    this.coursesSharedCollection = this.courseService.addCourseToCollectionIfMissing<ICourse>(
      this.coursesSharedCollection,
      enrolment.course,
    );
    this.studentsSharedCollection = this.studentService.addStudentToCollectionIfMissing<IStudent>(
      this.studentsSharedCollection,
      enrolment.student,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.courseService
      .query()
      .pipe(map((res: HttpResponse<ICourse[]>) => res.body ?? []))
      .pipe(map((courses: ICourse[]) => this.courseService.addCourseToCollectionIfMissing<ICourse>(courses, this.enrolment?.course)))
      .subscribe((courses: ICourse[]) => (this.coursesSharedCollection = courses));

    this.studentService
      .query()
      .pipe(map((res: HttpResponse<IStudent[]>) => res.body ?? []))
      .pipe(map((students: IStudent[]) => this.studentService.addStudentToCollectionIfMissing<IStudent>(students, this.enrolment?.student)))
      .subscribe((students: IStudent[]) => (this.studentsSharedCollection = students));
  }
}

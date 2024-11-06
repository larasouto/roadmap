import { Component, OnInit, inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IStudentIdCard } from '../student-id-card.model';
import { StudentIdCardService } from '../service/student-id-card.service';
import { StudentIdCardFormGroup, StudentIdCardFormService } from './student-id-card-form.service';

@Component({
  standalone: true,
  selector: 'jhi-student-id-card-update',
  templateUrl: './student-id-card-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class StudentIdCardUpdateComponent implements OnInit {
  isSaving = false;
  studentIdCard: IStudentIdCard | null = null;

  protected studentIdCardService = inject(StudentIdCardService);
  protected studentIdCardFormService = inject(StudentIdCardFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: StudentIdCardFormGroup = this.studentIdCardFormService.createStudentIdCardFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studentIdCard }) => {
      this.studentIdCard = studentIdCard;
      if (studentIdCard) {
        this.updateForm(studentIdCard);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const studentIdCard = this.studentIdCardFormService.getStudentIdCard(this.editForm);
    if (studentIdCard.id !== null) {
      this.subscribeToSaveResponse(this.studentIdCardService.update(studentIdCard));
    } else {
      this.subscribeToSaveResponse(this.studentIdCardService.create(studentIdCard));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStudentIdCard>>): void {
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

  protected updateForm(studentIdCard: IStudentIdCard): void {
    this.studentIdCard = studentIdCard;
    this.studentIdCardFormService.resetForm(this.editForm, studentIdCard);
  }
}

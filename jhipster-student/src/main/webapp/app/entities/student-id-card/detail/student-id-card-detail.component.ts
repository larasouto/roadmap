import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IStudentIdCard } from '../student-id-card.model';

@Component({
  standalone: true,
  selector: 'jhi-student-id-card-detail',
  templateUrl: './student-id-card-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class StudentIdCardDetailComponent {
  studentIdCard = input<IStudentIdCard | null>(null);

  previousState(): void {
    window.history.back();
  }
}

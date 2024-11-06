import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from 'app/shared/date';
import { IEnrolment } from '../enrolment.model';

@Component({
  standalone: true,
  selector: 'jhi-enrolment-detail',
  templateUrl: './enrolment-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EnrolmentDetailComponent {
  enrolment = input<IEnrolment | null>(null);

  previousState(): void {
    window.history.back();
  }
}

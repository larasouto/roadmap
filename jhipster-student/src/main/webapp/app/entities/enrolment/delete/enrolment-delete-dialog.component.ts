import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEnrolment } from '../enrolment.model';
import { EnrolmentService } from '../service/enrolment.service';

@Component({
  standalone: true,
  templateUrl: './enrolment-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EnrolmentDeleteDialogComponent {
  enrolment?: IEnrolment;

  protected enrolmentService = inject(EnrolmentService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.enrolmentService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

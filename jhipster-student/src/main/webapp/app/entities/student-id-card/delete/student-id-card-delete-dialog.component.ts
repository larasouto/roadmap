import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IStudentIdCard } from '../student-id-card.model';
import { StudentIdCardService } from '../service/student-id-card.service';

@Component({
  standalone: true,
  templateUrl: './student-id-card-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class StudentIdCardDeleteDialogComponent {
  studentIdCard?: IStudentIdCard;

  protected studentIdCardService = inject(StudentIdCardService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.studentIdCardService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

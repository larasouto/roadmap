import { IStudentIdCard } from 'app/entities/student-id-card/student-id-card.model';

export interface IStudent {
  id: number;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  age?: number | null;
  studentIdCard?: IStudentIdCard | null;
}

export type NewStudent = Omit<IStudent, 'id'> & { id: null };

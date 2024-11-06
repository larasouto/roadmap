import dayjs from 'dayjs/esm';
import { IStudent } from 'app/entities/student/student.model';

export interface IBook {
  id: number;
  createdAt?: dayjs.Dayjs | null;
  bookName?: string | null;
  student?: IStudent | null;
}

export type NewBook = Omit<IBook, 'id'> & { id: null };

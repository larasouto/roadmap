import dayjs from 'dayjs/esm';
import { ICourse } from 'app/entities/course/course.model';
import { IStudent } from 'app/entities/student/student.model';

export interface IEnrolment {
  id: number;
  createdAt?: dayjs.Dayjs | null;
  course?: ICourse | null;
  student?: IStudent | null;
}

export type NewEnrolment = Omit<IEnrolment, 'id'> & { id: null };

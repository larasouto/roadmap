export interface ICourse {
  id: number;
  name?: string | null;
  department?: string | null;
}

export type NewCourse = Omit<ICourse, 'id'> & { id: null };

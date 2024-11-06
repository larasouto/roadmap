export interface IStudentIdCard {
  id: number;
  cardNumber?: string | null;
}

export type NewStudentIdCard = Omit<IStudentIdCard, 'id'> & { id: null };

import { IStudentIdCard, NewStudentIdCard } from './student-id-card.model';

export const sampleWithRequiredData: IStudentIdCard = {
  id: 16,
  cardNumber: 'how knottily su',
};

export const sampleWithPartialData: IStudentIdCard = {
  id: 12001,
  cardNumber: 'hyphenation inn',
};

export const sampleWithFullData: IStudentIdCard = {
  id: 20216,
  cardNumber: 'portrayXXXXXXXX',
};

export const sampleWithNewData: NewStudentIdCard = {
  cardNumber: 'yowza unaccount',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

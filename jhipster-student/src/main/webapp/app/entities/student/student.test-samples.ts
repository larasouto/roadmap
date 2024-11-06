import { IStudent, NewStudent } from './student.model';

export const sampleWithRequiredData: IStudent = {
  id: 17910,
  firstName: 'Earline',
  lastName: 'Gulgowski',
  email: 'Loyal_Jerde76@hotmail.com',
  age: 29174,
};

export const sampleWithPartialData: IStudent = {
  id: 1849,
  firstName: 'Harmon',
  lastName: 'Gislason',
  email: 'Blanche54@hotmail.com',
  age: 21057,
};

export const sampleWithFullData: IStudent = {
  id: 16365,
  firstName: 'Raegan',
  lastName: 'Glover',
  email: 'Rylee25@gmail.com',
  age: 22840,
};

export const sampleWithNewData: NewStudent = {
  firstName: 'Rebecca',
  lastName: 'Stehr',
  email: 'Geo31@gmail.com',
  age: 4203,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import { ICourse, NewCourse } from './course.model';

export const sampleWithRequiredData: ICourse = {
  id: 7637,
  name: 'ape painfully',
  department: 'highly generously',
};

export const sampleWithPartialData: ICourse = {
  id: 28455,
  name: 'mosh yet pronoun',
  department: 'and generously aw',
};

export const sampleWithFullData: ICourse = {
  id: 10434,
  name: 'furthermore rationalise',
  department: 'besides circular sunder',
};

export const sampleWithNewData: NewCourse = {
  name: 'taut verify until',
  department: 'precious qua',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

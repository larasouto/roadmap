import dayjs from 'dayjs/esm';

import { IEnrolment, NewEnrolment } from './enrolment.model';

export const sampleWithRequiredData: IEnrolment = {
  id: 4955,
  createdAt: dayjs('2024-11-06T08:25'),
};

export const sampleWithPartialData: IEnrolment = {
  id: 17811,
  createdAt: dayjs('2024-11-06T00:15'),
};

export const sampleWithFullData: IEnrolment = {
  id: 19248,
  createdAt: dayjs('2024-11-06T14:16'),
};

export const sampleWithNewData: NewEnrolment = {
  createdAt: dayjs('2024-11-05T19:01'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

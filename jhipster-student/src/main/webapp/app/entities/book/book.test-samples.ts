import dayjs from 'dayjs/esm';

import { IBook, NewBook } from './book.model';

export const sampleWithRequiredData: IBook = {
  id: 11932,
  createdAt: dayjs('2024-11-06T07:03'),
  bookName: 'save',
};

export const sampleWithPartialData: IBook = {
  id: 4156,
  createdAt: dayjs('2024-11-06T01:02'),
  bookName: 'needily before motivate',
};

export const sampleWithFullData: IBook = {
  id: 19934,
  createdAt: dayjs('2024-11-06T04:50'),
  bookName: 'redress viability shoulder',
};

export const sampleWithNewData: NewBook = {
  createdAt: dayjs('2024-11-06T00:16'),
  bookName: 'internal woot jeopardise',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

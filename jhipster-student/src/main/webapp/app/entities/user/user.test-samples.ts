import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 21706,
  login: '5d3',
};

export const sampleWithPartialData: IUser = {
  id: 10941,
  login: '+caBG@22Z0w',
};

export const sampleWithFullData: IUser = {
  id: 15421,
  login: 'grj1ng',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

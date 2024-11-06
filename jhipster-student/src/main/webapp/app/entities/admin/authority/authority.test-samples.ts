import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'ca28894a-22fa-4a30-a14a-3d4893dd99cc',
};

export const sampleWithPartialData: IAuthority = {
  name: 'aa2c2fc0-dd36-4690-9739-f3fd265f1f99',
};

export const sampleWithFullData: IAuthority = {
  name: '94c4ac51-90c1-4f3f-bf67-8b643e8bdb27',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

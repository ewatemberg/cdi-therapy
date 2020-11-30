import { ISeccionC } from 'app/shared/model/seccion-c.model';

export interface IFormaVerbal {
  id?: number;
  forma?: string;
  seccionC?: ISeccionC;
}

export const defaultValue: Readonly<IFormaVerbal> = {};

import { ISeccionC } from 'app/shared/model/seccion-c.model';

export interface IFormaVerbal {
  id?: number;
  forma?: string | null;
  seccionCS?: ISeccionC[] | null;
}

export const defaultValue: Readonly<IFormaVerbal> = {};

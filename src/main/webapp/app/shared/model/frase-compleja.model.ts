import { ISeccionD } from 'app/shared/model/seccion-d.model';

export interface IFraseCompleja {
  id?: number;
  frase?: string | null;
  seccionDS?: ISeccionD[] | null;
}

export const defaultValue: Readonly<IFraseCompleja> = {};

import { ISeccionD } from 'app/shared/model/seccion-d.model';

export interface IFraseCompleja {
  id?: number;
  frase?: string;
  seccionD?: ISeccionD;
}

export const defaultValue: Readonly<IFraseCompleja> = {};

import { ISeccionB } from 'app/shared/model/seccion-b.model';

export interface IUsoLenguaje {
  id?: number;
  pregunta?: string | null;
  seccionBS?: ISeccionB[] | null;
}

export const defaultValue: Readonly<IUsoLenguaje> = {};

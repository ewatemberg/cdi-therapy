import { ISeccionB } from 'app/shared/model/seccion-b.model';

export interface IUsoLenguaje {
  id?: number;
  pregunta?: string;
  seccionB?: ISeccionB;
}

export const defaultValue: Readonly<IUsoLenguaje> = {};

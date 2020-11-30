import { IUsoLenguaje } from 'app/shared/model/uso-lenguaje.model';
import { ICuestionario } from 'app/shared/model/cuestionario.model';

export interface ISeccionB {
  id?: number;
  valor?: number;
  usoLenguajes?: IUsoLenguaje[];
  cuestionario?: ICuestionario;
}

export const defaultValue: Readonly<ISeccionB> = {};

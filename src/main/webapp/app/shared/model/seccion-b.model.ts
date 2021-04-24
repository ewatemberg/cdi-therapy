import { ICuestionario } from 'app/shared/model/cuestionario.model';
import { IUsoLenguaje } from 'app/shared/model/uso-lenguaje.model';

export interface ISeccionB {
  id?: number;
  valor?: number | null;
  cuestionario?: ICuestionario | null;
  usoLenguaje?: IUsoLenguaje | null;
}

export const defaultValue: Readonly<ISeccionB> = {};

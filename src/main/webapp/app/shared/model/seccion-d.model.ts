import { ICuestionario } from 'app/shared/model/cuestionario.model';
import { IFraseCompleja } from 'app/shared/model/frase-compleja.model';

export interface ISeccionD {
  id?: number;
  valor?: number | null;
  cuestionario?: ICuestionario | null;
  fraseCompleja?: IFraseCompleja | null;
}

export const defaultValue: Readonly<ISeccionD> = {};

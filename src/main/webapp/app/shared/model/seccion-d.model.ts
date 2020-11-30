import { IFraseCompleja } from 'app/shared/model/frase-compleja.model';
import { ICuestionario } from 'app/shared/model/cuestionario.model';

export interface ISeccionD {
  id?: number;
  valor?: number;
  fraseComplejas?: IFraseCompleja[];
  cuestionario?: ICuestionario;
}

export const defaultValue: Readonly<ISeccionD> = {};

import { IFormaVerbal } from 'app/shared/model/forma-verbal.model';
import { ICuestionario } from 'app/shared/model/cuestionario.model';

export interface ISeccionC {
  id?: number;
  descripcion?: string;
  valor?: number;
  formaVerbals?: IFormaVerbal[];
  cuestionario?: ICuestionario;
}

export const defaultValue: Readonly<ISeccionC> = {};

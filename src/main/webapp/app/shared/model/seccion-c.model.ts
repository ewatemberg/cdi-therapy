import { ICuestionario } from 'app/shared/model/cuestionario.model';
import { IFormaVerbal } from 'app/shared/model/forma-verbal.model';

export interface ISeccionC {
  id?: number;
  descripcion?: string | null;
  valor?: number | null;
  cuestionario?: ICuestionario | null;
  formaVerbal?: IFormaVerbal | null;
}

export const defaultValue: Readonly<ISeccionC> = {};

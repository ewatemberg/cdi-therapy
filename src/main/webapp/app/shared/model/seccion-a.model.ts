import { ICuestionario } from 'app/shared/model/cuestionario.model';
import { IVocabulario } from 'app/shared/model/vocabulario.model';

export interface ISeccionA {
  id?: number;
  descripcion?: string | null;
  chequeado?: boolean | null;
  cuestionario?: ICuestionario | null;
  vocabulario?: IVocabulario | null;
}

export const defaultValue: Readonly<ISeccionA> = {
  chequeado: false,
};

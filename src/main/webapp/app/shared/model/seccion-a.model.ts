import { IVocabulario } from 'app/shared/model/vocabulario.model';
import { ICuestionario } from 'app/shared/model/cuestionario.model';

export interface ISeccionA {
  id?: number;
  descripcion?: string;
  chequeado?: boolean;
  vocabularios?: IVocabulario[];
  cuestionario?: ICuestionario;
}

export const defaultValue: Readonly<ISeccionA> = {
  chequeado: false,
};

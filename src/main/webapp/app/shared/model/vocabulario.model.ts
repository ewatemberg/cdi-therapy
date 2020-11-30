import { ISeccionA } from 'app/shared/model/seccion-a.model';
import { CategoriaSemantica } from 'app/shared/model/enumerations/categoria-semantica.model';

export interface IVocabulario {
  id?: number;
  palabra?: string;
  categoria?: CategoriaSemantica;
  seccionA?: ISeccionA;
}

export const defaultValue: Readonly<IVocabulario> = {};

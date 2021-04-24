import { ISeccionA } from 'app/shared/model/seccion-a.model';
import { ISeccionB } from 'app/shared/model/seccion-b.model';
import { ISeccionC } from 'app/shared/model/seccion-c.model';
import { ISeccionD } from 'app/shared/model/seccion-d.model';
import { IPaciente } from 'app/shared/model/paciente.model';

export interface ICuestionario {
  id?: number;
  seccionAS?: ISeccionA[] | null;
  seccionBS?: ISeccionB[] | null;
  seccionCS?: ISeccionC[] | null;
  seccionDS?: ISeccionD[] | null;
  paciente?: IPaciente | null;
}

export const defaultValue: Readonly<ICuestionario> = {};

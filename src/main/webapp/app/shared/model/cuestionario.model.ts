import { ISeccionA } from 'app/shared/model/seccion-a.model';
import { ISeccionB } from 'app/shared/model/seccion-b.model';
import { ISeccionC } from 'app/shared/model/seccion-c.model';
import { ISeccionD } from 'app/shared/model/seccion-d.model';
import { IPaciente } from 'app/shared/model/paciente.model';

export interface ICuestionario {
  id?: number;
  seccionAS?: ISeccionA[];
  seccionBS?: ISeccionB[];
  seccionCS?: ISeccionC[];
  seccionDS?: ISeccionD[];
  paciente?: IPaciente;
}

export const defaultValue: Readonly<ICuestionario> = {};

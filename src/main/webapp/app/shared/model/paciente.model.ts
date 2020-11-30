import { Moment } from 'moment';
import { ICuestionario } from 'app/shared/model/cuestionario.model';

export interface IPaciente {
  id?: number;
  nombres?: string;
  apellidos?: string;
  obraSocial?: string;
  dni?: string;
  fechaNacimiento?: string;
  lugarNacimiento?: string;
  genero?: string;
  nacioAntes9Meses?: boolean;
  semanasGestacion?: number;
  pesoAlNacer?: number;
  enfermedadAuditivaLenguaje?: boolean;
  descripcionProblemaAuditivoLenguaje?: string;
  infeccionesOido?: boolean;
  totalInfeccionesAnual?: number;
  problemaSalud?: boolean;
  descripcionProblemaSalud?: string;
  nombreMadre?: string;
  edadMadre?: number;
  lugarOrigenMadre?: string;
  nombrePadre?: string;
  edadPadre?: number;
  lugarOrigenPadre?: string;
  cuestionarios?: ICuestionario[];
}

export const defaultValue: Readonly<IPaciente> = {
  nacioAntes9Meses: false,
  enfermedadAuditivaLenguaje: false,
  infeccionesOido: false,
  problemaSalud: false,
};

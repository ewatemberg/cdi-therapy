import dayjs from 'dayjs';
import { ICuestionario } from 'app/shared/model/cuestionario.model';

export interface IPaciente {
  id?: number;
  nombres?: string | null;
  apellidos?: string | null;
  obraSocial?: string | null;
  dni?: string | null;
  fechaNacimiento?: string | null;
  lugarNacimiento?: string | null;
  genero?: string | null;
  nacioAntes9Meses?: boolean | null;
  semanasGestacion?: number | null;
  pesoAlNacer?: number | null;
  enfermedadAuditivaLenguaje?: boolean | null;
  descripcionProblemaAuditivoLenguaje?: string | null;
  infeccionesOido?: boolean | null;
  totalInfeccionesAnual?: number | null;
  problemaSalud?: boolean | null;
  descripcionProblemaSalud?: string | null;
  nombreMadre?: string | null;
  edadMadre?: number | null;
  lugarOrigenMadre?: string | null;
  nombrePadre?: string | null;
  edadPadre?: number | null;
  lugarOrigenPadre?: string | null;
  cuestionarios?: ICuestionario[] | null;
}

export const defaultValue: Readonly<IPaciente> = {
  nacioAntes9Meses: false,
  enfermedadAuditivaLenguaje: false,
  infeccionesOido: false,
  problemaSalud: false,
};

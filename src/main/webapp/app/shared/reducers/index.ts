import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import paciente, {
  PacienteState
} from 'app/entities/paciente/paciente.reducer';
// prettier-ignore
import seccionA, {
  SeccionAState
} from 'app/entities/seccion-a/seccion-a.reducer';
// prettier-ignore
import seccionB, {
  SeccionBState
} from 'app/entities/seccion-b/seccion-b.reducer';
// prettier-ignore
import seccionC, {
  SeccionCState
} from 'app/entities/seccion-c/seccion-c.reducer';
// prettier-ignore
import seccionD, {
  SeccionDState
} from 'app/entities/seccion-d/seccion-d.reducer';
// prettier-ignore
import vocabulario, {
  VocabularioState
} from 'app/entities/vocabulario/vocabulario.reducer';
// prettier-ignore
import usoLenguaje, {
  UsoLenguajeState
} from 'app/entities/uso-lenguaje/uso-lenguaje.reducer';
// prettier-ignore
import formaVerbal, {
  FormaVerbalState
} from 'app/entities/forma-verbal/forma-verbal.reducer';
// prettier-ignore
import fraseCompleja, {
  FraseComplejaState
} from 'app/entities/frase-compleja/frase-compleja.reducer';
// prettier-ignore
import cuestionario, {
  CuestionarioState
} from 'app/entities/cuestionario/cuestionario.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly paciente: PacienteState;
  readonly seccionA: SeccionAState;
  readonly seccionB: SeccionBState;
  readonly seccionC: SeccionCState;
  readonly seccionD: SeccionDState;
  readonly vocabulario: VocabularioState;
  readonly usoLenguaje: UsoLenguajeState;
  readonly formaVerbal: FormaVerbalState;
  readonly fraseCompleja: FraseComplejaState;
  readonly cuestionario: CuestionarioState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  paciente,
  seccionA,
  seccionB,
  seccionC,
  seccionD,
  vocabulario,
  usoLenguaje,
  formaVerbal,
  fraseCompleja,
  cuestionario,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;

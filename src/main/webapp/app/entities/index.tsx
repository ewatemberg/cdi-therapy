import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Paciente from './paciente';
import SeccionA from './seccion-a';
import SeccionB from './seccion-b';
import SeccionC from './seccion-c';
import SeccionD from './seccion-d';
import Vocabulario from './vocabulario';
import UsoLenguaje from './uso-lenguaje';
import FormaVerbal from './forma-verbal';
import FraseCompleja from './frase-compleja';
import Cuestionario from './cuestionario';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}paciente`} component={Paciente} />
      <ErrorBoundaryRoute path={`${match.url}seccion-a`} component={SeccionA} />
      <ErrorBoundaryRoute path={`${match.url}seccion-b`} component={SeccionB} />
      <ErrorBoundaryRoute path={`${match.url}seccion-c`} component={SeccionC} />
      <ErrorBoundaryRoute path={`${match.url}seccion-d`} component={SeccionD} />
      <ErrorBoundaryRoute path={`${match.url}vocabulario`} component={Vocabulario} />
      <ErrorBoundaryRoute path={`${match.url}uso-lenguaje`} component={UsoLenguaje} />
      <ErrorBoundaryRoute path={`${match.url}forma-verbal`} component={FormaVerbal} />
      <ErrorBoundaryRoute path={`${match.url}frase-compleja`} component={FraseCompleja} />
      <ErrorBoundaryRoute path={`${match.url}cuestionario`} component={Cuestionario} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;

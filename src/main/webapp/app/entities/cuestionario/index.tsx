import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cuestionario from './cuestionario';
import CuestionarioDetail from './cuestionario-detail';
import CuestionarioUpdate from './cuestionario-update';
import CuestionarioDeleteDialog from './cuestionario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CuestionarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CuestionarioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CuestionarioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cuestionario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CuestionarioDeleteDialog} />
  </>
);

export default Routes;

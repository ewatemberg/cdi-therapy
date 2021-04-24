import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SeccionB from './seccion-b';
import SeccionBDetail from './seccion-b-detail';
import SeccionBUpdate from './seccion-b-update';
import SeccionBDeleteDialog from './seccion-b-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeccionBUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeccionBUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeccionBDetail} />
      <ErrorBoundaryRoute path={match.url} component={SeccionB} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeccionBDeleteDialog} />
  </>
);

export default Routes;

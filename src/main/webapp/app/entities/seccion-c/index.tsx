import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SeccionC from './seccion-c';
import SeccionCDetail from './seccion-c-detail';
import SeccionCUpdate from './seccion-c-update';
import SeccionCDeleteDialog from './seccion-c-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeccionCUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeccionCUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeccionCDetail} />
      <ErrorBoundaryRoute path={match.url} component={SeccionC} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeccionCDeleteDialog} />
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SeccionA from './seccion-a';
import SeccionADetail from './seccion-a-detail';
import SeccionAUpdate from './seccion-a-update';
import SeccionADeleteDialog from './seccion-a-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeccionAUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeccionAUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeccionADetail} />
      <ErrorBoundaryRoute path={match.url} component={SeccionA} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeccionADeleteDialog} />
  </>
);

export default Routes;

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SeccionD from './seccion-d';
import SeccionDDetail from './seccion-d-detail';
import SeccionDUpdate from './seccion-d-update';
import SeccionDDeleteDialog from './seccion-d-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SeccionDUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SeccionDUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SeccionDDetail} />
      <ErrorBoundaryRoute path={match.url} component={SeccionD} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SeccionDDeleteDialog} />
  </>
);

export default Routes;

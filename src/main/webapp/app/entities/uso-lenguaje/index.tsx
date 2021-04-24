import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UsoLenguaje from './uso-lenguaje';
import UsoLenguajeDetail from './uso-lenguaje-detail';
import UsoLenguajeUpdate from './uso-lenguaje-update';
import UsoLenguajeDeleteDialog from './uso-lenguaje-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UsoLenguajeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UsoLenguajeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UsoLenguajeDetail} />
      <ErrorBoundaryRoute path={match.url} component={UsoLenguaje} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UsoLenguajeDeleteDialog} />
  </>
);

export default Routes;

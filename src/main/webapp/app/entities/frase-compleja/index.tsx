import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FraseCompleja from './frase-compleja';
import FraseComplejaDetail from './frase-compleja-detail';
import FraseComplejaUpdate from './frase-compleja-update';
import FraseComplejaDeleteDialog from './frase-compleja-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FraseComplejaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FraseComplejaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FraseComplejaDetail} />
      <ErrorBoundaryRoute path={match.url} component={FraseCompleja} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FraseComplejaDeleteDialog} />
  </>
);

export default Routes;

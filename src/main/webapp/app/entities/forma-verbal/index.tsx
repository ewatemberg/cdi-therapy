import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FormaVerbal from './forma-verbal';
import FormaVerbalDetail from './forma-verbal-detail';
import FormaVerbalUpdate from './forma-verbal-update';
import FormaVerbalDeleteDialog from './forma-verbal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FormaVerbalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FormaVerbalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FormaVerbalDetail} />
      <ErrorBoundaryRoute path={match.url} component={FormaVerbal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FormaVerbalDeleteDialog} />
  </>
);

export default Routes;

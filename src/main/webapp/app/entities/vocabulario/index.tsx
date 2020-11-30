import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Vocabulario from './vocabulario';
import VocabularioDetail from './vocabulario-detail';
import VocabularioUpdate from './vocabulario-update';
import VocabularioDeleteDialog from './vocabulario-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VocabularioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VocabularioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VocabularioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Vocabulario} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={VocabularioDeleteDialog} />
  </>
);

export default Routes;

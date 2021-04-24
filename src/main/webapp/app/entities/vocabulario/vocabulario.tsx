import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vocabulario.reducer';
import { IVocabulario } from 'app/shared/model/vocabulario.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVocabularioProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Vocabulario = (props: IVocabularioProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { vocabularioList, match, loading } = props;
  return (
    <div>
      <h2 id="vocabulario-heading" data-cy="VocabularioHeading">
        <Translate contentKey="cdiApp.vocabulario.home.title">Vocabularios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="cdiApp.vocabulario.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="cdiApp.vocabulario.home.createLabel">Create new Vocabulario</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vocabularioList && vocabularioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="cdiApp.vocabulario.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.vocabulario.palabra">Palabra</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.vocabulario.categoria">Categoria</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vocabularioList.map((vocabulario, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${vocabulario.id}`} color="link" size="sm">
                      {vocabulario.id}
                    </Button>
                  </td>
                  <td>{vocabulario.palabra}</td>
                  <td>
                    <Translate contentKey={`cdiApp.CategoriaSemantica.${vocabulario.categoria}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vocabulario.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vocabulario.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vocabulario.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cdiApp.vocabulario.home.notFound">No Vocabularios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ vocabulario }: IRootState) => ({
  vocabularioList: vocabulario.entities,
  loading: vocabulario.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Vocabulario);

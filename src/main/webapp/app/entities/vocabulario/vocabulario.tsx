import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
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

  const { vocabularioList, match, loading } = props;
  return (
    <div>
      <h2 id="vocabulario-heading">
        <Translate contentKey="cdiApp.vocabulario.home.title">Vocabularios</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cdiApp.vocabulario.home.createLabel">Create new Vocabulario</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {vocabularioList && vocabularioList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.vocabulario.palabra">Palabra</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.vocabulario.categoria">Categoria</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.vocabulario.seccionA">Seccion A</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vocabularioList.map((vocabulario, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${vocabulario.id}`} color="link" size="sm">
                      {vocabulario.id}
                    </Button>
                  </td>
                  <td>{vocabulario.palabra}</td>
                  <td>
                    <Translate contentKey={`cdiApp.CategoriaSemantica.${vocabulario.categoria}`} />
                  </td>
                  <td>{vocabulario.seccionA ? <Link to={`seccion-a/${vocabulario.seccionA.id}`}>{vocabulario.seccionA.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${vocabulario.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vocabulario.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${vocabulario.id}/delete`} color="danger" size="sm">
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

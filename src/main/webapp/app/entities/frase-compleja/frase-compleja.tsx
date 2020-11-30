import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './frase-compleja.reducer';
import { IFraseCompleja } from 'app/shared/model/frase-compleja.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFraseComplejaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const FraseCompleja = (props: IFraseComplejaProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { fraseComplejaList, match, loading } = props;
  return (
    <div>
      <h2 id="frase-compleja-heading">
        <Translate contentKey="cdiApp.fraseCompleja.home.title">Frase Complejas</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cdiApp.fraseCompleja.home.createLabel">Create new Frase Compleja</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {fraseComplejaList && fraseComplejaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.fraseCompleja.frase">Frase</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.fraseCompleja.seccionD">Seccion D</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fraseComplejaList.map((fraseCompleja, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${fraseCompleja.id}`} color="link" size="sm">
                      {fraseCompleja.id}
                    </Button>
                  </td>
                  <td>{fraseCompleja.frase}</td>
                  <td>
                    {fraseCompleja.seccionD ? <Link to={`seccion-d/${fraseCompleja.seccionD.id}`}>{fraseCompleja.seccionD.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${fraseCompleja.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fraseCompleja.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${fraseCompleja.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="cdiApp.fraseCompleja.home.notFound">No Frase Complejas found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ fraseCompleja }: IRootState) => ({
  fraseComplejaList: fraseCompleja.entities,
  loading: fraseCompleja.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FraseCompleja);

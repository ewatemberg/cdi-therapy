import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './uso-lenguaje.reducer';
import { IUsoLenguaje } from 'app/shared/model/uso-lenguaje.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUsoLenguajeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UsoLenguaje = (props: IUsoLenguajeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { usoLenguajeList, match, loading } = props;
  return (
    <div>
      <h2 id="uso-lenguaje-heading">
        <Translate contentKey="cdiApp.usoLenguaje.home.title">Uso Lenguajes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cdiApp.usoLenguaje.home.createLabel">Create new Uso Lenguaje</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {usoLenguajeList && usoLenguajeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.usoLenguaje.pregunta">Pregunta</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.usoLenguaje.seccionB">Seccion B</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {usoLenguajeList.map((usoLenguaje, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${usoLenguaje.id}`} color="link" size="sm">
                      {usoLenguaje.id}
                    </Button>
                  </td>
                  <td>{usoLenguaje.pregunta}</td>
                  <td>{usoLenguaje.seccionB ? <Link to={`seccion-b/${usoLenguaje.seccionB.id}`}>{usoLenguaje.seccionB.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${usoLenguaje.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${usoLenguaje.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${usoLenguaje.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="cdiApp.usoLenguaje.home.notFound">No Uso Lenguajes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ usoLenguaje }: IRootState) => ({
  usoLenguajeList: usoLenguaje.entities,
  loading: usoLenguaje.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsoLenguaje);

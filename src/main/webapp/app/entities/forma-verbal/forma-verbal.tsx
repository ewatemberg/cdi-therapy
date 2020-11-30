import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './forma-verbal.reducer';
import { IFormaVerbal } from 'app/shared/model/forma-verbal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFormaVerbalProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const FormaVerbal = (props: IFormaVerbalProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { formaVerbalList, match, loading } = props;
  return (
    <div>
      <h2 id="forma-verbal-heading">
        <Translate contentKey="cdiApp.formaVerbal.home.title">Forma Verbals</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cdiApp.formaVerbal.home.createLabel">Create new Forma Verbal</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {formaVerbalList && formaVerbalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.formaVerbal.forma">Forma</Translate>
                </th>
                <th>
                  <Translate contentKey="cdiApp.formaVerbal.seccionC">Seccion C</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {formaVerbalList.map((formaVerbal, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${formaVerbal.id}`} color="link" size="sm">
                      {formaVerbal.id}
                    </Button>
                  </td>
                  <td>{formaVerbal.forma}</td>
                  <td>{formaVerbal.seccionC ? <Link to={`seccion-c/${formaVerbal.seccionC.id}`}>{formaVerbal.seccionC.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${formaVerbal.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${formaVerbal.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${formaVerbal.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="cdiApp.formaVerbal.home.notFound">No Forma Verbals found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ formaVerbal }: IRootState) => ({
  formaVerbalList: formaVerbal.entities,
  loading: formaVerbal.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormaVerbal);

import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './seccion-a.reducer';
import { ISeccionA } from 'app/shared/model/seccion-a.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISeccionADetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeccionADetail = (props: ISeccionADetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { seccionAEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cdiApp.seccionA.detail.title">SeccionA</Translate> [<b>{seccionAEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="descripcion">
              <Translate contentKey="cdiApp.seccionA.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{seccionAEntity.descripcion}</dd>
          <dt>
            <span id="chequeado">
              <Translate contentKey="cdiApp.seccionA.chequeado">Chequeado</Translate>
            </span>
          </dt>
          <dd>{seccionAEntity.chequeado ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="cdiApp.seccionA.cuestionario">Cuestionario</Translate>
          </dt>
          <dd>{seccionAEntity.cuestionario ? seccionAEntity.cuestionario.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/seccion-a" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/seccion-a/${seccionAEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ seccionA }: IRootState) => ({
  seccionAEntity: seccionA.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeccionADetail);

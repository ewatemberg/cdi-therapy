import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './seccion-d.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISeccionDDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeccionDDetail = (props: ISeccionDDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { seccionDEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="seccionDDetailsHeading">
          <Translate contentKey="cdiApp.seccionD.detail.title">SeccionD</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{seccionDEntity.id}</dd>
          <dt>
            <span id="valor">
              <Translate contentKey="cdiApp.seccionD.valor">Valor</Translate>
            </span>
          </dt>
          <dd>{seccionDEntity.valor}</dd>
          <dt>
            <Translate contentKey="cdiApp.seccionD.cuestionario">Cuestionario</Translate>
          </dt>
          <dd>{seccionDEntity.cuestionario ? seccionDEntity.cuestionario.id : ''}</dd>
          <dt>
            <Translate contentKey="cdiApp.seccionD.fraseCompleja">Frase Compleja</Translate>
          </dt>
          <dd>{seccionDEntity.fraseCompleja ? seccionDEntity.fraseCompleja.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/seccion-d" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/seccion-d/${seccionDEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ seccionD }: IRootState) => ({
  seccionDEntity: seccionD.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeccionDDetail);

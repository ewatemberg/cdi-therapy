import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cuestionario.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICuestionarioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CuestionarioDetail = (props: ICuestionarioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { cuestionarioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cuestionarioDetailsHeading">
          <Translate contentKey="cdiApp.cuestionario.detail.title">Cuestionario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cuestionarioEntity.id}</dd>
          <dt>
            <Translate contentKey="cdiApp.cuestionario.paciente">Paciente</Translate>
          </dt>
          <dd>{cuestionarioEntity.paciente ? cuestionarioEntity.paciente.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/cuestionario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cuestionario/${cuestionarioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ cuestionario }: IRootState) => ({
  cuestionarioEntity: cuestionario.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CuestionarioDetail);

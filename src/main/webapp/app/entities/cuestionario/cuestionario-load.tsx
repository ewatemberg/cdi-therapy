import './cuestionario.scss';

import React, {useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {Translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';

import {IRootState} from 'app/shared/reducers';
import {getEntity} from './cuestionario.reducer';
import {APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT} from 'app/config/constants';

export interface ICuestionarioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const CuestionarioLoad = (props: ICuestionarioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const {cuestionarioEntity} = props;
  return (
    <div>
      <Row>
        <Col sm="12" md={{size: 6, offset: 3}}>
          <div style={{textAlign: "center"}}>
            <h2 data-cy="cuestionarioDetailsHeading" style={{marginBottom: 22}}>
              <Translate contentKey="cdiApp.cuestionario.load.title">Inventario del Desarrollo Comunicativo
                MacArthur-Bates</Translate>
            </h2>
            <h3>
              <Translate contentKey="cdiApp.cuestionario.load.subtitle">Forma II Palabras y oraciones</Translate>
            </h3>
          </div>
        </Col>
      </Row>
      <Row>
        <Col md="12">
          {/*agregar la informacion del paciente*/}
        </Col>
      </Row>
      <Row>
        <Col md="12">
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
            <FontAwesomeIcon icon="arrow-left"/>{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/cuestionario/${cuestionarioEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt"/>{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({cuestionario}: IRootState) => ({
  cuestionarioEntity: cuestionario.entity,
});

const mapDispatchToProps = {getEntity};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CuestionarioLoad);

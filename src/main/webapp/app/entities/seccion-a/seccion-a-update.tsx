import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICuestionario } from 'app/shared/model/cuestionario.model';
import { getEntities as getCuestionarios } from 'app/entities/cuestionario/cuestionario.reducer';
import { getEntity, updateEntity, createEntity, reset } from './seccion-a.reducer';
import { ISeccionA } from 'app/shared/model/seccion-a.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISeccionAUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeccionAUpdate = (props: ISeccionAUpdateProps) => {
  const [cuestionarioId, setCuestionarioId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { seccionAEntity, cuestionarios, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/seccion-a' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCuestionarios();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...seccionAEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="cdiApp.seccionA.home.createOrEditLabel">
            <Translate contentKey="cdiApp.seccionA.home.createOrEditLabel">Create or edit a SeccionA</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : seccionAEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="seccion-a-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="seccion-a-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descripcionLabel" for="seccion-a-descripcion">
                  <Translate contentKey="cdiApp.seccionA.descripcion">Descripcion</Translate>
                </Label>
                <AvField id="seccion-a-descripcion" type="text" name="descripcion" />
              </AvGroup>
              <AvGroup check>
                <Label id="chequeadoLabel">
                  <AvInput id="seccion-a-chequeado" type="checkbox" className="form-check-input" name="chequeado" />
                  <Translate contentKey="cdiApp.seccionA.chequeado">Chequeado</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="seccion-a-cuestionario">
                  <Translate contentKey="cdiApp.seccionA.cuestionario">Cuestionario</Translate>
                </Label>
                <AvInput id="seccion-a-cuestionario" type="select" className="form-control" name="cuestionario.id">
                  <option value="" key="0" />
                  {cuestionarios
                    ? cuestionarios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/seccion-a" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  cuestionarios: storeState.cuestionario.entities,
  seccionAEntity: storeState.seccionA.entity,
  loading: storeState.seccionA.loading,
  updating: storeState.seccionA.updating,
  updateSuccess: storeState.seccionA.updateSuccess,
});

const mapDispatchToProps = {
  getCuestionarios,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeccionAUpdate);

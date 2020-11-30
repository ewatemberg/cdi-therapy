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
import { getEntity, updateEntity, createEntity, reset } from './seccion-d.reducer';
import { ISeccionD } from 'app/shared/model/seccion-d.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISeccionDUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeccionDUpdate = (props: ISeccionDUpdateProps) => {
  const [cuestionarioId, setCuestionarioId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { seccionDEntity, cuestionarios, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/seccion-d' + props.location.search);
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
        ...seccionDEntity,
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
          <h2 id="cdiApp.seccionD.home.createOrEditLabel">
            <Translate contentKey="cdiApp.seccionD.home.createOrEditLabel">Create or edit a SeccionD</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : seccionDEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="seccion-d-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="seccion-d-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valorLabel" for="seccion-d-valor">
                  <Translate contentKey="cdiApp.seccionD.valor">Valor</Translate>
                </Label>
                <AvField id="seccion-d-valor" type="string" className="form-control" name="valor" />
              </AvGroup>
              <AvGroup>
                <Label for="seccion-d-cuestionario">
                  <Translate contentKey="cdiApp.seccionD.cuestionario">Cuestionario</Translate>
                </Label>
                <AvInput id="seccion-d-cuestionario" type="select" className="form-control" name="cuestionario.id">
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
              <Button tag={Link} id="cancel-save" to="/seccion-d" replace color="info">
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
  seccionDEntity: storeState.seccionD.entity,
  loading: storeState.seccionD.loading,
  updating: storeState.seccionD.updating,
  updateSuccess: storeState.seccionD.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(SeccionDUpdate);

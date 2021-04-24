import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICuestionario } from 'app/shared/model/cuestionario.model';
import { getEntities as getCuestionarios } from 'app/entities/cuestionario/cuestionario.reducer';
import { IUsoLenguaje } from 'app/shared/model/uso-lenguaje.model';
import { getEntities as getUsoLenguajes } from 'app/entities/uso-lenguaje/uso-lenguaje.reducer';
import { getEntity, updateEntity, createEntity, reset } from './seccion-b.reducer';
import { ISeccionB } from 'app/shared/model/seccion-b.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISeccionBUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SeccionBUpdate = (props: ISeccionBUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { seccionBEntity, cuestionarios, usoLenguajes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/seccion-b' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getCuestionarios();
    props.getUsoLenguajes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...seccionBEntity,
        ...values,
        cuestionario: cuestionarios.find(it => it.id.toString() === values.cuestionarioId.toString()),
        usoLenguaje: usoLenguajes.find(it => it.id.toString() === values.usoLenguajeId.toString()),
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
          <h2 id="cdiApp.seccionB.home.createOrEditLabel" data-cy="SeccionBCreateUpdateHeading">
            <Translate contentKey="cdiApp.seccionB.home.createOrEditLabel">Create or edit a SeccionB</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : seccionBEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="seccion-b-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="seccion-b-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="valorLabel" for="seccion-b-valor">
                  <Translate contentKey="cdiApp.seccionB.valor">Valor</Translate>
                </Label>
                <AvField id="seccion-b-valor" data-cy="valor" type="string" className="form-control" name="valor" />
              </AvGroup>
              <AvGroup>
                <Label for="seccion-b-cuestionario">
                  <Translate contentKey="cdiApp.seccionB.cuestionario">Cuestionario</Translate>
                </Label>
                <AvInput id="seccion-b-cuestionario" data-cy="cuestionario" type="select" className="form-control" name="cuestionarioId">
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
              <AvGroup>
                <Label for="seccion-b-usoLenguaje">
                  <Translate contentKey="cdiApp.seccionB.usoLenguaje">Uso Lenguaje</Translate>
                </Label>
                <AvInput id="seccion-b-usoLenguaje" data-cy="usoLenguaje" type="select" className="form-control" name="usoLenguajeId">
                  <option value="" key="0" />
                  {usoLenguajes
                    ? usoLenguajes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/seccion-b" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
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
  usoLenguajes: storeState.usoLenguaje.entities,
  seccionBEntity: storeState.seccionB.entity,
  loading: storeState.seccionB.loading,
  updating: storeState.seccionB.updating,
  updateSuccess: storeState.seccionB.updateSuccess,
});

const mapDispatchToProps = {
  getCuestionarios,
  getUsoLenguajes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SeccionBUpdate);

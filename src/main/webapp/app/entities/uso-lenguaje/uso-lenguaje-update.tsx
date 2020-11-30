import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISeccionB } from 'app/shared/model/seccion-b.model';
import { getEntities as getSeccionBs } from 'app/entities/seccion-b/seccion-b.reducer';
import { getEntity, updateEntity, createEntity, reset } from './uso-lenguaje.reducer';
import { IUsoLenguaje } from 'app/shared/model/uso-lenguaje.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUsoLenguajeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UsoLenguajeUpdate = (props: IUsoLenguajeUpdateProps) => {
  const [seccionBId, setSeccionBId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { usoLenguajeEntity, seccionBS, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/uso-lenguaje');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSeccionBs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...usoLenguajeEntity,
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
          <h2 id="cdiApp.usoLenguaje.home.createOrEditLabel">
            <Translate contentKey="cdiApp.usoLenguaje.home.createOrEditLabel">Create or edit a UsoLenguaje</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : usoLenguajeEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="uso-lenguaje-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="uso-lenguaje-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="preguntaLabel" for="uso-lenguaje-pregunta">
                  <Translate contentKey="cdiApp.usoLenguaje.pregunta">Pregunta</Translate>
                </Label>
                <AvField id="uso-lenguaje-pregunta" type="text" name="pregunta" />
              </AvGroup>
              <AvGroup>
                <Label for="uso-lenguaje-seccionB">
                  <Translate contentKey="cdiApp.usoLenguaje.seccionB">Seccion B</Translate>
                </Label>
                <AvInput id="uso-lenguaje-seccionB" type="select" className="form-control" name="seccionB.id">
                  <option value="" key="0" />
                  {seccionBS
                    ? seccionBS.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/uso-lenguaje" replace color="info">
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
  seccionBS: storeState.seccionB.entities,
  usoLenguajeEntity: storeState.usoLenguaje.entity,
  loading: storeState.usoLenguaje.loading,
  updating: storeState.usoLenguaje.updating,
  updateSuccess: storeState.usoLenguaje.updateSuccess,
});

const mapDispatchToProps = {
  getSeccionBs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UsoLenguajeUpdate);

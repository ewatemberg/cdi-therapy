import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISeccionD } from 'app/shared/model/seccion-d.model';
import { getEntities as getSeccionDs } from 'app/entities/seccion-d/seccion-d.reducer';
import { getEntity, updateEntity, createEntity, reset } from './frase-compleja.reducer';
import { IFraseCompleja } from 'app/shared/model/frase-compleja.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFraseComplejaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FraseComplejaUpdate = (props: IFraseComplejaUpdateProps) => {
  const [seccionDId, setSeccionDId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { fraseComplejaEntity, seccionDS, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/frase-compleja');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSeccionDs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...fraseComplejaEntity,
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
          <h2 id="cdiApp.fraseCompleja.home.createOrEditLabel">
            <Translate contentKey="cdiApp.fraseCompleja.home.createOrEditLabel">Create or edit a FraseCompleja</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : fraseComplejaEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="frase-compleja-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="frase-compleja-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="fraseLabel" for="frase-compleja-frase">
                  <Translate contentKey="cdiApp.fraseCompleja.frase">Frase</Translate>
                </Label>
                <AvField id="frase-compleja-frase" type="text" name="frase" />
              </AvGroup>
              <AvGroup>
                <Label for="frase-compleja-seccionD">
                  <Translate contentKey="cdiApp.fraseCompleja.seccionD">Seccion D</Translate>
                </Label>
                <AvInput id="frase-compleja-seccionD" type="select" className="form-control" name="seccionD.id">
                  <option value="" key="0" />
                  {seccionDS
                    ? seccionDS.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/frase-compleja" replace color="info">
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
  seccionDS: storeState.seccionD.entities,
  fraseComplejaEntity: storeState.fraseCompleja.entity,
  loading: storeState.fraseCompleja.loading,
  updating: storeState.fraseCompleja.updating,
  updateSuccess: storeState.fraseCompleja.updateSuccess,
});

const mapDispatchToProps = {
  getSeccionDs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FraseComplejaUpdate);

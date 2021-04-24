import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './frase-compleja.reducer';
import { IFraseCompleja } from 'app/shared/model/frase-compleja.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFraseComplejaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FraseComplejaUpdate = (props: IFraseComplejaUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { fraseComplejaEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/frase-compleja');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
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
          <h2 id="cdiApp.fraseCompleja.home.createOrEditLabel" data-cy="FraseComplejaCreateUpdateHeading">
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
                <AvField id="frase-compleja-frase" data-cy="frase" type="text" name="frase" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/frase-compleja" replace color="info">
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
  fraseComplejaEntity: storeState.fraseCompleja.entity,
  loading: storeState.fraseCompleja.loading,
  updating: storeState.fraseCompleja.updating,
  updateSuccess: storeState.fraseCompleja.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FraseComplejaUpdate);

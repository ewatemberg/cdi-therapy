import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './forma-verbal.reducer';
import { IFormaVerbal } from 'app/shared/model/forma-verbal.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFormaVerbalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FormaVerbalUpdate = (props: IFormaVerbalUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { formaVerbalEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/forma-verbal');
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
        ...formaVerbalEntity,
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
          <h2 id="cdiApp.formaVerbal.home.createOrEditLabel" data-cy="FormaVerbalCreateUpdateHeading">
            <Translate contentKey="cdiApp.formaVerbal.home.createOrEditLabel">Create or edit a FormaVerbal</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : formaVerbalEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="forma-verbal-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="forma-verbal-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="formaLabel" for="forma-verbal-forma">
                  <Translate contentKey="cdiApp.formaVerbal.forma">Forma</Translate>
                </Label>
                <AvField id="forma-verbal-forma" data-cy="forma" type="text" name="forma" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/forma-verbal" replace color="info">
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
  formaVerbalEntity: storeState.formaVerbal.entity,
  loading: storeState.formaVerbal.loading,
  updating: storeState.formaVerbal.updating,
  updateSuccess: storeState.formaVerbal.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormaVerbalUpdate);

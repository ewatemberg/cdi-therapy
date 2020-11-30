import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISeccionA } from 'app/shared/model/seccion-a.model';
import { getEntities as getSeccionAs } from 'app/entities/seccion-a/seccion-a.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vocabulario.reducer';
import { IVocabulario } from 'app/shared/model/vocabulario.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVocabularioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VocabularioUpdate = (props: IVocabularioUpdateProps) => {
  const [seccionAId, setSeccionAId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { vocabularioEntity, seccionAS, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/vocabulario');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getSeccionAs();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...vocabularioEntity,
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
          <h2 id="cdiApp.vocabulario.home.createOrEditLabel">
            <Translate contentKey="cdiApp.vocabulario.home.createOrEditLabel">Create or edit a Vocabulario</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : vocabularioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="vocabulario-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="vocabulario-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="palabraLabel" for="vocabulario-palabra">
                  <Translate contentKey="cdiApp.vocabulario.palabra">Palabra</Translate>
                </Label>
                <AvField id="vocabulario-palabra" type="text" name="palabra" />
              </AvGroup>
              <AvGroup>
                <Label id="categoriaLabel" for="vocabulario-categoria">
                  <Translate contentKey="cdiApp.vocabulario.categoria">Categoria</Translate>
                </Label>
                <AvInput
                  id="vocabulario-categoria"
                  type="select"
                  className="form-control"
                  name="categoria"
                  value={(!isNew && vocabularioEntity.categoria) || 'SONIDOS'}
                >
                  <option value="SONIDOS">{translate('cdiApp.CategoriaSemantica.SONIDOS')}</option>
                  <option value="ANIMAL">{translate('cdiApp.CategoriaSemantica.ANIMAL')}</option>
                  <option value="VEHICULOS">{translate('cdiApp.CategoriaSemantica.VEHICULOS')}</option>
                  <option value="ALIMENTACION">{translate('cdiApp.CategoriaSemantica.ALIMENTACION')}</option>
                  <option value="ROPA">{translate('cdiApp.CategoriaSemantica.ROPA')}</option>
                  <option value="CUERPO">{translate('cdiApp.CategoriaSemantica.CUERPO')}</option>
                  <option value="JUGUETES">{translate('cdiApp.CategoriaSemantica.JUGUETES')}</option>
                  <option value="COCINA">{translate('cdiApp.CategoriaSemantica.COCINA')}</option>
                  <option value="MUEBLE_CUARTO">{translate('cdiApp.CategoriaSemantica.MUEBLE_CUARTO')}</option>
                  <option value="LUGAR_OBJETO">{translate('cdiApp.CategoriaSemantica.LUGAR_OBJETO')}</option>
                  <option value="PERSONAS">{translate('cdiApp.CategoriaSemantica.PERSONAS')}</option>
                  <option value="RUTINAS_JUEGOS">{translate('cdiApp.CategoriaSemantica.RUTINAS_JUEGOS')}</option>
                  <option value="ACCIONES_PROCESOS">{translate('cdiApp.CategoriaSemantica.ACCIONES_PROCESOS')}</option>
                  <option value="TIEMPO">{translate('cdiApp.CategoriaSemantica.TIEMPO')}</option>
                  <option value="CUALIDADES_ATRIBUTOS">{translate('cdiApp.CategoriaSemantica.CUALIDADES_ATRIBUTOS')}</option>
                  <option value="PRONOMBRES">{translate('cdiApp.CategoriaSemantica.PRONOMBRES')}</option>
                  <option value="PREGUNTAS">{translate('cdiApp.CategoriaSemantica.PREGUNTAS')}</option>
                  <option value="PREPOSICIONES_ARTICULOS">{translate('cdiApp.CategoriaSemantica.PREPOSICIONES_ARTICULOS')}</option>
                  <option value="CUALIFICADORES_ADVERBIOS">{translate('cdiApp.CategoriaSemantica.CUALIFICADORES_ADVERBIOS')}</option>
                  <option value="LOCATIVOS">{translate('cdiApp.CategoriaSemantica.LOCATIVOS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="vocabulario-seccionA">
                  <Translate contentKey="cdiApp.vocabulario.seccionA">Seccion A</Translate>
                </Label>
                <AvInput id="vocabulario-seccionA" type="select" className="form-control" name="seccionA.id">
                  <option value="" key="0" />
                  {seccionAS
                    ? seccionAS.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/vocabulario" replace color="info">
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
  seccionAS: storeState.seccionA.entities,
  vocabularioEntity: storeState.vocabulario.entity,
  loading: storeState.vocabulario.loading,
  updating: storeState.vocabulario.updating,
  updateSuccess: storeState.vocabulario.updateSuccess,
});

const mapDispatchToProps = {
  getSeccionAs,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VocabularioUpdate);

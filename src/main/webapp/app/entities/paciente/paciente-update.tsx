import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './paciente.reducer';
import { IPaciente } from 'app/shared/model/paciente.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPacienteUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PacienteUpdate = (props: IPacienteUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { pacienteEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/paciente' + props.location.search);
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
        ...pacienteEntity,
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
          <h2 id="cdiApp.paciente.home.createOrEditLabel" data-cy="PacienteCreateUpdateHeading">
            <Translate contentKey="cdiApp.paciente.home.createOrEditLabel">Create or edit a Paciente</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : pacienteEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="paciente-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="paciente-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nombresLabel" for="paciente-nombres">
                  <Translate contentKey="cdiApp.paciente.nombres">Nombres</Translate>
                </Label>
                <AvField id="paciente-nombres" data-cy="nombres" type="text" name="nombres" />
              </AvGroup>
              <AvGroup>
                <Label id="apellidosLabel" for="paciente-apellidos">
                  <Translate contentKey="cdiApp.paciente.apellidos">Apellidos</Translate>
                </Label>
                <AvField id="paciente-apellidos" data-cy="apellidos" type="text" name="apellidos" />
              </AvGroup>
              <AvGroup>
                <Label id="obraSocialLabel" for="paciente-obraSocial">
                  <Translate contentKey="cdiApp.paciente.obraSocial">Obra Social</Translate>
                </Label>
                <AvField id="paciente-obraSocial" data-cy="obraSocial" type="text" name="obraSocial" />
              </AvGroup>
              <AvGroup>
                <Label id="dniLabel" for="paciente-dni">
                  <Translate contentKey="cdiApp.paciente.dni">Dni</Translate>
                </Label>
                <AvField id="paciente-dni" data-cy="dni" type="text" name="dni" />
              </AvGroup>
              <AvGroup>
                <Label id="fechaNacimientoLabel" for="paciente-fechaNacimiento">
                  <Translate contentKey="cdiApp.paciente.fechaNacimiento">Fecha Nacimiento</Translate>
                </Label>
                <AvField
                  id="paciente-fechaNacimiento"
                  data-cy="fechaNacimiento"
                  type="date"
                  className="form-control"
                  name="fechaNacimiento"
                />
              </AvGroup>
              <AvGroup>
                <Label id="lugarNacimientoLabel" for="paciente-lugarNacimiento">
                  <Translate contentKey="cdiApp.paciente.lugarNacimiento">Lugar Nacimiento</Translate>
                </Label>
                <AvField id="paciente-lugarNacimiento" data-cy="lugarNacimiento" type="text" name="lugarNacimiento" />
              </AvGroup>
              <AvGroup>
                <Label id="generoLabel" for="paciente-genero">
                  <Translate contentKey="cdiApp.paciente.genero">Genero</Translate>
                </Label>
                <AvField id="paciente-genero" data-cy="genero" type="text" name="genero" />
              </AvGroup>
              <AvGroup check>
                <Label id="nacioAntes9MesesLabel">
                  <AvInput
                    id="paciente-nacioAntes9Meses"
                    data-cy="nacioAntes9Meses"
                    type="checkbox"
                    className="form-check-input"
                    name="nacioAntes9Meses"
                  />
                  <Translate contentKey="cdiApp.paciente.nacioAntes9Meses">Nacio Antes 9 Meses</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="semanasGestacionLabel" for="paciente-semanasGestacion">
                  <Translate contentKey="cdiApp.paciente.semanasGestacion">Semanas Gestacion</Translate>
                </Label>
                <AvField
                  id="paciente-semanasGestacion"
                  data-cy="semanasGestacion"
                  type="string"
                  className="form-control"
                  name="semanasGestacion"
                />
              </AvGroup>
              <AvGroup>
                <Label id="pesoAlNacerLabel" for="paciente-pesoAlNacer">
                  <Translate contentKey="cdiApp.paciente.pesoAlNacer">Peso Al Nacer</Translate>
                </Label>
                <AvField id="paciente-pesoAlNacer" data-cy="pesoAlNacer" type="text" name="pesoAlNacer" />
              </AvGroup>
              <AvGroup check>
                <Label id="enfermedadAuditivaLenguajeLabel">
                  <AvInput
                    id="paciente-enfermedadAuditivaLenguaje"
                    data-cy="enfermedadAuditivaLenguaje"
                    type="checkbox"
                    className="form-check-input"
                    name="enfermedadAuditivaLenguaje"
                  />
                  <Translate contentKey="cdiApp.paciente.enfermedadAuditivaLenguaje">Enfermedad Auditiva Lenguaje</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="descripcionProblemaAuditivoLenguajeLabel" for="paciente-descripcionProblemaAuditivoLenguaje">
                  <Translate contentKey="cdiApp.paciente.descripcionProblemaAuditivoLenguaje">
                    Descripcion Problema Auditivo Lenguaje
                  </Translate>
                </Label>
                <AvField
                  id="paciente-descripcionProblemaAuditivoLenguaje"
                  data-cy="descripcionProblemaAuditivoLenguaje"
                  type="text"
                  name="descripcionProblemaAuditivoLenguaje"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="infeccionesOidoLabel">
                  <AvInput
                    id="paciente-infeccionesOido"
                    data-cy="infeccionesOido"
                    type="checkbox"
                    className="form-check-input"
                    name="infeccionesOido"
                  />
                  <Translate contentKey="cdiApp.paciente.infeccionesOido">Infecciones Oido</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="totalInfeccionesAnualLabel" for="paciente-totalInfeccionesAnual">
                  <Translate contentKey="cdiApp.paciente.totalInfeccionesAnual">Total Infecciones Anual</Translate>
                </Label>
                <AvField
                  id="paciente-totalInfeccionesAnual"
                  data-cy="totalInfeccionesAnual"
                  type="string"
                  className="form-control"
                  name="totalInfeccionesAnual"
                />
              </AvGroup>
              <AvGroup check>
                <Label id="problemaSaludLabel">
                  <AvInput
                    id="paciente-problemaSalud"
                    data-cy="problemaSalud"
                    type="checkbox"
                    className="form-check-input"
                    name="problemaSalud"
                  />
                  <Translate contentKey="cdiApp.paciente.problemaSalud">Problema Salud</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="descripcionProblemaSaludLabel" for="paciente-descripcionProblemaSalud">
                  <Translate contentKey="cdiApp.paciente.descripcionProblemaSalud">Descripcion Problema Salud</Translate>
                </Label>
                <AvField
                  id="paciente-descripcionProblemaSalud"
                  data-cy="descripcionProblemaSalud"
                  type="text"
                  name="descripcionProblemaSalud"
                />
              </AvGroup>
              <AvGroup>
                <Label id="nombreMadreLabel" for="paciente-nombreMadre">
                  <Translate contentKey="cdiApp.paciente.nombreMadre">Nombre Madre</Translate>
                </Label>
                <AvField id="paciente-nombreMadre" data-cy="nombreMadre" type="text" name="nombreMadre" />
              </AvGroup>
              <AvGroup>
                <Label id="edadMadreLabel" for="paciente-edadMadre">
                  <Translate contentKey="cdiApp.paciente.edadMadre">Edad Madre</Translate>
                </Label>
                <AvField id="paciente-edadMadre" data-cy="edadMadre" type="string" className="form-control" name="edadMadre" />
              </AvGroup>
              <AvGroup>
                <Label id="lugarOrigenMadreLabel" for="paciente-lugarOrigenMadre">
                  <Translate contentKey="cdiApp.paciente.lugarOrigenMadre">Lugar Origen Madre</Translate>
                </Label>
                <AvField id="paciente-lugarOrigenMadre" data-cy="lugarOrigenMadre" type="text" name="lugarOrigenMadre" />
              </AvGroup>
              <AvGroup>
                <Label id="nombrePadreLabel" for="paciente-nombrePadre">
                  <Translate contentKey="cdiApp.paciente.nombrePadre">Nombre Padre</Translate>
                </Label>
                <AvField id="paciente-nombrePadre" data-cy="nombrePadre" type="text" name="nombrePadre" />
              </AvGroup>
              <AvGroup>
                <Label id="edadPadreLabel" for="paciente-edadPadre">
                  <Translate contentKey="cdiApp.paciente.edadPadre">Edad Padre</Translate>
                </Label>
                <AvField id="paciente-edadPadre" data-cy="edadPadre" type="string" className="form-control" name="edadPadre" />
              </AvGroup>
              <AvGroup>
                <Label id="lugarOrigenPadreLabel" for="paciente-lugarOrigenPadre">
                  <Translate contentKey="cdiApp.paciente.lugarOrigenPadre">Lugar Origen Padre</Translate>
                </Label>
                <AvField id="paciente-lugarOrigenPadre" data-cy="lugarOrigenPadre" type="text" name="lugarOrigenPadre" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/paciente" replace color="info">
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
  pacienteEntity: storeState.paciente.entity,
  loading: storeState.paciente.loading,
  updating: storeState.paciente.updating,
  updateSuccess: storeState.paciente.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PacienteUpdate);

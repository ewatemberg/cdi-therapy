import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './paciente.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPacienteDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PacienteDetail = (props: IPacienteDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { pacienteEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pacienteDetailsHeading">
          <Translate contentKey="cdiApp.paciente.detail.title">Paciente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.id}</dd>
          <dt>
            <span id="nombres">
              <Translate contentKey="cdiApp.paciente.nombres">Nombres</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.nombres}</dd>
          <dt>
            <span id="apellidos">
              <Translate contentKey="cdiApp.paciente.apellidos">Apellidos</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.apellidos}</dd>
          <dt>
            <span id="obraSocial">
              <Translate contentKey="cdiApp.paciente.obraSocial">Obra Social</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.obraSocial}</dd>
          <dt>
            <span id="dni">
              <Translate contentKey="cdiApp.paciente.dni">Dni</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.dni}</dd>
          <dt>
            <span id="fechaNacimiento">
              <Translate contentKey="cdiApp.paciente.fechaNacimiento">Fecha Nacimiento</Translate>
            </span>
          </dt>
          <dd>
            {pacienteEntity.fechaNacimiento ? (
              <TextFormat value={pacienteEntity.fechaNacimiento} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lugarNacimiento">
              <Translate contentKey="cdiApp.paciente.lugarNacimiento">Lugar Nacimiento</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.lugarNacimiento}</dd>
          <dt>
            <span id="genero">
              <Translate contentKey="cdiApp.paciente.genero">Genero</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.genero}</dd>
          <dt>
            <span id="nacioAntes9Meses">
              <Translate contentKey="cdiApp.paciente.nacioAntes9Meses">Nacio Antes 9 Meses</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.nacioAntes9Meses ? 'true' : 'false'}</dd>
          <dt>
            <span id="semanasGestacion">
              <Translate contentKey="cdiApp.paciente.semanasGestacion">Semanas Gestacion</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.semanasGestacion}</dd>
          <dt>
            <span id="pesoAlNacer">
              <Translate contentKey="cdiApp.paciente.pesoAlNacer">Peso Al Nacer</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.pesoAlNacer}</dd>
          <dt>
            <span id="enfermedadAuditivaLenguaje">
              <Translate contentKey="cdiApp.paciente.enfermedadAuditivaLenguaje">Enfermedad Auditiva Lenguaje</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.enfermedadAuditivaLenguaje ? 'true' : 'false'}</dd>
          <dt>
            <span id="descripcionProblemaAuditivoLenguaje">
              <Translate contentKey="cdiApp.paciente.descripcionProblemaAuditivoLenguaje">Descripcion Problema Auditivo Lenguaje</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.descripcionProblemaAuditivoLenguaje}</dd>
          <dt>
            <span id="infeccionesOido">
              <Translate contentKey="cdiApp.paciente.infeccionesOido">Infecciones Oido</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.infeccionesOido ? 'true' : 'false'}</dd>
          <dt>
            <span id="totalInfeccionesAnual">
              <Translate contentKey="cdiApp.paciente.totalInfeccionesAnual">Total Infecciones Anual</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.totalInfeccionesAnual}</dd>
          <dt>
            <span id="problemaSalud">
              <Translate contentKey="cdiApp.paciente.problemaSalud">Problema Salud</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.problemaSalud ? 'true' : 'false'}</dd>
          <dt>
            <span id="descripcionProblemaSalud">
              <Translate contentKey="cdiApp.paciente.descripcionProblemaSalud">Descripcion Problema Salud</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.descripcionProblemaSalud}</dd>
          <dt>
            <span id="nombreMadre">
              <Translate contentKey="cdiApp.paciente.nombreMadre">Nombre Madre</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.nombreMadre}</dd>
          <dt>
            <span id="edadMadre">
              <Translate contentKey="cdiApp.paciente.edadMadre">Edad Madre</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.edadMadre}</dd>
          <dt>
            <span id="lugarOrigenMadre">
              <Translate contentKey="cdiApp.paciente.lugarOrigenMadre">Lugar Origen Madre</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.lugarOrigenMadre}</dd>
          <dt>
            <span id="nombrePadre">
              <Translate contentKey="cdiApp.paciente.nombrePadre">Nombre Padre</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.nombrePadre}</dd>
          <dt>
            <span id="edadPadre">
              <Translate contentKey="cdiApp.paciente.edadPadre">Edad Padre</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.edadPadre}</dd>
          <dt>
            <span id="lugarOrigenPadre">
              <Translate contentKey="cdiApp.paciente.lugarOrigenPadre">Lugar Origen Padre</Translate>
            </span>
          </dt>
          <dd>{pacienteEntity.lugarOrigenPadre}</dd>
        </dl>
        <Button tag={Link} to="/paciente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/paciente/${pacienteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ paciente }: IRootState) => ({
  pacienteEntity: paciente.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PacienteDetail);

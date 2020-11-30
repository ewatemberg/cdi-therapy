import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './paciente.reducer';
import { IPaciente } from 'app/shared/model/paciente.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IPacienteProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Paciente = (props: IPacienteProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { pacienteList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="paciente-heading">
        <Translate contentKey="cdiApp.paciente.home.title">Pacientes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="cdiApp.paciente.home.createLabel">Create new Paciente</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {pacienteList && pacienteList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombres')}>
                  <Translate contentKey="cdiApp.paciente.nombres">Nombres</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('apellidos')}>
                  <Translate contentKey="cdiApp.paciente.apellidos">Apellidos</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('obraSocial')}>
                  <Translate contentKey="cdiApp.paciente.obraSocial">Obra Social</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dni')}>
                  <Translate contentKey="cdiApp.paciente.dni">Dni</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fechaNacimiento')}>
                  <Translate contentKey="cdiApp.paciente.fechaNacimiento">Fecha Nacimiento</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lugarNacimiento')}>
                  <Translate contentKey="cdiApp.paciente.lugarNacimiento">Lugar Nacimiento</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('genero')}>
                  <Translate contentKey="cdiApp.paciente.genero">Genero</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nacioAntes9Meses')}>
                  <Translate contentKey="cdiApp.paciente.nacioAntes9Meses">Nacio Antes 9 Meses</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('semanasGestacion')}>
                  <Translate contentKey="cdiApp.paciente.semanasGestacion">Semanas Gestacion</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pesoAlNacer')}>
                  <Translate contentKey="cdiApp.paciente.pesoAlNacer">Peso Al Nacer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('enfermedadAuditivaLenguaje')}>
                  <Translate contentKey="cdiApp.paciente.enfermedadAuditivaLenguaje">Enfermedad Auditiva Lenguaje</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descripcionProblemaAuditivoLenguaje')}>
                  <Translate contentKey="cdiApp.paciente.descripcionProblemaAuditivoLenguaje">
                    Descripcion Problema Auditivo Lenguaje
                  </Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('infeccionesOido')}>
                  <Translate contentKey="cdiApp.paciente.infeccionesOido">Infecciones Oido</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalInfeccionesAnual')}>
                  <Translate contentKey="cdiApp.paciente.totalInfeccionesAnual">Total Infecciones Anual</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('problemaSalud')}>
                  <Translate contentKey="cdiApp.paciente.problemaSalud">Problema Salud</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('descripcionProblemaSalud')}>
                  <Translate contentKey="cdiApp.paciente.descripcionProblemaSalud">Descripcion Problema Salud</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombreMadre')}>
                  <Translate contentKey="cdiApp.paciente.nombreMadre">Nombre Madre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('edadMadre')}>
                  <Translate contentKey="cdiApp.paciente.edadMadre">Edad Madre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lugarOrigenMadre')}>
                  <Translate contentKey="cdiApp.paciente.lugarOrigenMadre">Lugar Origen Madre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('nombrePadre')}>
                  <Translate contentKey="cdiApp.paciente.nombrePadre">Nombre Padre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('edadPadre')}>
                  <Translate contentKey="cdiApp.paciente.edadPadre">Edad Padre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lugarOrigenPadre')}>
                  <Translate contentKey="cdiApp.paciente.lugarOrigenPadre">Lugar Origen Padre</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pacienteList.map((paciente, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${paciente.id}`} color="link" size="sm">
                      {paciente.id}
                    </Button>
                  </td>
                  <td>{paciente.nombres}</td>
                  <td>{paciente.apellidos}</td>
                  <td>{paciente.obraSocial}</td>
                  <td>{paciente.dni}</td>
                  <td>
                    {paciente.fechaNacimiento ? (
                      <TextFormat type="date" value={paciente.fechaNacimiento} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{paciente.lugarNacimiento}</td>
                  <td>{paciente.genero}</td>
                  <td>{paciente.nacioAntes9Meses ? 'true' : 'false'}</td>
                  <td>{paciente.semanasGestacion}</td>
                  <td>{paciente.pesoAlNacer}</td>
                  <td>{paciente.enfermedadAuditivaLenguaje ? 'true' : 'false'}</td>
                  <td>{paciente.descripcionProblemaAuditivoLenguaje}</td>
                  <td>{paciente.infeccionesOido ? 'true' : 'false'}</td>
                  <td>{paciente.totalInfeccionesAnual}</td>
                  <td>{paciente.problemaSalud ? 'true' : 'false'}</td>
                  <td>{paciente.descripcionProblemaSalud}</td>
                  <td>{paciente.nombreMadre}</td>
                  <td>{paciente.edadMadre}</td>
                  <td>{paciente.lugarOrigenMadre}</td>
                  <td>{paciente.nombrePadre}</td>
                  <td>{paciente.edadPadre}</td>
                  <td>{paciente.lugarOrigenPadre}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${paciente.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${paciente.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${paciente.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="cdiApp.paciente.home.notFound">No Pacientes found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={pacienteList && pacienteList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ paciente }: IRootState) => ({
  pacienteList: paciente.entities,
  loading: paciente.loading,
  totalItems: paciente.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Paciente);

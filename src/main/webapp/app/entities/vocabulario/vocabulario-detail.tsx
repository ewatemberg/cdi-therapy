import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vocabulario.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVocabularioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const VocabularioDetail = (props: IVocabularioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { vocabularioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vocabularioDetailsHeading">
          <Translate contentKey="cdiApp.vocabulario.detail.title">Vocabulario</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vocabularioEntity.id}</dd>
          <dt>
            <span id="palabra">
              <Translate contentKey="cdiApp.vocabulario.palabra">Palabra</Translate>
            </span>
          </dt>
          <dd>{vocabularioEntity.palabra}</dd>
          <dt>
            <span id="categoria">
              <Translate contentKey="cdiApp.vocabulario.categoria">Categoria</Translate>
            </span>
          </dt>
          <dd>{vocabularioEntity.categoria}</dd>
        </dl>
        <Button tag={Link} to="/vocabulario" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vocabulario/${vocabularioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ vocabulario }: IRootState) => ({
  vocabularioEntity: vocabulario.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(VocabularioDetail);

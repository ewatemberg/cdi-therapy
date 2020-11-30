import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './forma-verbal.reducer';
import { IFormaVerbal } from 'app/shared/model/forma-verbal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFormaVerbalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FormaVerbalDetail = (props: IFormaVerbalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { formaVerbalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="cdiApp.formaVerbal.detail.title">FormaVerbal</Translate> [<b>{formaVerbalEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="forma">
              <Translate contentKey="cdiApp.formaVerbal.forma">Forma</Translate>
            </span>
          </dt>
          <dd>{formaVerbalEntity.forma}</dd>
          <dt>
            <Translate contentKey="cdiApp.formaVerbal.seccionC">Seccion C</Translate>
          </dt>
          <dd>{formaVerbalEntity.seccionC ? formaVerbalEntity.seccionC.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/forma-verbal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/forma-verbal/${formaVerbalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ formaVerbal }: IRootState) => ({
  formaVerbalEntity: formaVerbal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FormaVerbalDetail);

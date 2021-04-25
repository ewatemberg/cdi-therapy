import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          Copyright © 2021 <a href="https://codapli.frlp.utn.edu.ar/">Codapli</a> All Rights Reserved.
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
